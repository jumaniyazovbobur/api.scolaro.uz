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

    //    public ApiResponse<?> filter(AppApplicationFilterDTO dto)
    public AppApplicationResponseDTO toDTO(AppApplicationEntity entity) {
        AppApplicationResponseDTO dto = new AppApplicationResponseDTO();
        dto.setConsultingId(entity.getConsultingId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUniversityId(entity.getUniversityId());
        dto.setStudentId(entity.getStudentId());
        dto.setStatus(entity.getStatus());
        return dto;
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
        if (EntityDetails.hasRole(RoleEnum.ROLE_STUDENT, roleList)) {
            if (!currentId.equals(entity.getStudentId())) {
                log.info("ConsultingId or StudentId not found {}", id);
                return ApiResponse.forbidden("Your access denied for this Application!");
            }
        } else if (EntityDetails.hasRole(RoleEnum.ROLE_CONSULTING, roleList)) {
            if (!currentId.equals(entity.getConsultingId())) {
                log.info("ConsultingId or StudentId not found {}", id);
                return ApiResponse.forbidden("Your access denied for this Application!");
            }
        }

        AppApplicationResponseDTO dto = new AppApplicationResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setConsultingId(entity.getConsultingId());
        dto.setStudentId(entity.getStudentId());
        dto.setUniversityId(entity.getUniversityId());
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
            log.info("ConsultingId not found {}", id);
            return ApiResponse.forbidden("Your access denied for this Application!");
        }
        if (dto.getStatus().equals(AppStatus.FINISHED)) {
            appApplicationRepository.changeStatus(id, dto.getStatus(), LocalDateTime.now());
            return new ApiResponse<>(200,false,"Success");
        }
        appApplicationRepository.changeStatus(id, dto.getStatus());
        return new ApiResponse<>(200,false,"Success");
    }


}
