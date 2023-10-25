package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.appApplication.*;
import api.scolaro.uz.entity.AppApplicationEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.mapper.AppApplicationFilterMapperDTO;
import api.scolaro.uz.repository.appApplication.AppApplicationFilterRepository;
import api.scolaro.uz.repository.appApplication.AppApplicationRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static api.scolaro.uz.config.details.EntityDetails.getCurrentUserDetail;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppApplicationService {

    private final ConsultingService consultingService;
    private final UniversityService universityService;
    private final ProfileService profileService;
    private final AppApplicationRepository appApplicationRepository;
    private final AppApplicationFilterRepository appApplicationFilterRepository;

    public ApiResponse<?> create(AppApplicationRequestDTO dto) {
        ConsultingEntity consulting = consultingService.get(dto.getConsultingId());
        UniversityEntity university = universityService.get(dto.getUniversityId());

        AppApplicationEntity entity = new AppApplicationEntity();
        entity.setStudentId(EntityDetails.getCurrentUserId());
        entity.setConsultingId(consulting.getId());
        entity.setUniversityId(university.getId());
        entity.setStatus(AppStatus.TRAIL);

        appApplicationRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<?> filterForAdmin(AppApplicationFilterDTO filter, int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.filterForAdmin(filter, page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<?> filterForStudent(int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getForStudent(page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<Page<AppApplicationFilterMapperDTO>> filterForConsulting(AppApplicationFilterConsultingDTO dto, int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.filterForConsulting(dto, page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<AppApplicationResponseDTO> getById(String id) {
        Optional<AppApplicationEntity> optional = appApplicationRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("AppApplication not found {}", id);
            return new ApiResponse<>("AppApplication not found", 400, false);
        }

        AppApplicationEntity entity = optional.get();
        String currentId = EntityDetails.getCurrentUserId();

        List<String> roleList = Objects.requireNonNull(getCurrentUserDetail())
                .getRoleList()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();
        if (EntityDetails.hasRole(RoleEnum.ROLE_STUDENT, roleList) && !currentId.equals(entity.getStudentId())) {
            log.info("Profile do not have access to this application {} profileId {}", id, currentId);
            return ApiResponse.forbidden("Your access denied for this Application!");
        } else if (EntityDetails.hasRole(RoleEnum.ROLE_CONSULTING, roleList) && !currentId.equals(entity.getConsultingId())) {
            log.info("Consulting do not have access to this application {} consulting {}", id, currentId);
            return ApiResponse.forbidden("Your access denied for this Application!");
        }

        AppApplicationResponseDTO dto = new AppApplicationResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUniversityResponseDTO(universityService.getUniversityForApp(entity.getUniversityId()));
        dto.setConsultingResponseDTO(consultingService.getConsultingForApp(entity.getConsultingId()));
        dto.setProfileResponseDTO(profileService.getProfileForApp(entity.getStudentId()));

//        dto.setConsultingId(applicationEntity.getConsultingId());// TODO if requested is not consulting add consulting detail(id,name,photo,ownerdetail)
//        dto.setStudentId(applicationEntity.getStudentId()); // TODO If requested profile is not student then add student detail (id,name,surname,photo)
//        dto.setUniversityId(applicationEntity.getUniversityId()); // TODO add University detail (id,name,photo)
        return new ApiResponse<>(200, true, dto);
    }

    public ApiResponse<?> changeStatus(String id, AppApplicationChangeStatusDTO dto) {
        Optional<AppApplicationEntity> optional = appApplicationRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("AppApplication not found {}", id);
            return new ApiResponse<>("AppApplication not found", 400, false);
        }

        AppApplicationEntity entity = optional.get();
        String currentId = EntityDetails.getCurrentUserId();
        if (!currentId.equals(entity.getConsultingId())) {
            log.info("Consulting {}  do not have access to application {}", currentId, id);
            return ApiResponse.forbidden("Your access denied for this Application!");
        }
        if (entity.getStatus().equals(AppStatus.TRAIL)) {// TRAIL -> STARTED or CANCELED
            if (dto.getStatus().equals(AppStatus.STARTED)) {
                appApplicationRepository.statusToStarted(id);
            } else if (dto.getStatus().equals(AppStatus.CANCELED)) {
                appApplicationRepository.statusToCanceled(id);
            }
        }
        if (entity.getStatus().equals(AppStatus.STARTED)) { // STARTED -> FINISHED or CANCELED
            if (dto.getStatus().equals(AppStatus.FINISHED)) {
                appApplicationRepository.statusToFinished(id);
            } else if (dto.getStatus().equals(AppStatus.CANCELED)) {
                appApplicationRepository.statusToCanceled(id);
            }
        }
        return new ApiResponse<>(200, false, "Success");
    }

    public AppApplicationResponseDTO toDTO(AppApplicationEntity entity) {
        AppApplicationResponseDTO dto = new AppApplicationResponseDTO();
        dto.setConsultingId(entity.getConsultingId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUniversityId(entity.getUniversityId());
        dto.setStudentId(entity.getStudentId());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public AppApplicationEntity getByIdForSimpleMessage(String id){
        return appApplicationRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Application not Found");
            throw new ItemNotFoundException("Application not found");
        });
    }

}
