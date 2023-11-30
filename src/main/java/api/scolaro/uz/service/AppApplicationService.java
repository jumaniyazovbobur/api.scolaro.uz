package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.appApplication.*;
import api.scolaro.uz.entity.application.AppApplicationEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.consulting.ConsultingStepEntity;
import api.scolaro.uz.enums.*;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.mapper.AppApplicationFilterMapperDTO;
import api.scolaro.uz.repository.appApplication.AppApplicationFilterRepository;
import api.scolaro.uz.repository.appApplication.AppApplicationRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import api.scolaro.uz.service.consulting.ConsultingStepLevelService;
import api.scolaro.uz.service.consulting.ConsultingStepService;
import api.scolaro.uz.service.consulting.ConsultingTariffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private final ConsultingStepLevelService consultingStepLevelService;
    private final ResourceMessageService resourceMessageService;
    private final ConsultingStepService consultingStepService;
    private final ConsultingTariffService consultingTariffService;

    public ApiResponse<AppApplicationResponseDTO> create(AppApplicationRequestDTO dto) {
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

    public ApiResponse<Page<AppApplicationFilterMapperDTO>> filterForAdmin(AppApplicationFilterDTO filter, int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.filterForAdmin(filter, page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<Page<AppApplicationFilterMapperDTO>> filterForStudent(int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getForStudent(page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<Page<AppApplicationFilterMapperDTO>> filterForStudent_mobile(int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getForStudent_mobile(page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<Page<AppApplicationFilterMapperDTO>> filterForConsulting(AppApplicationFilterConsultingDTO dto, int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.filterForConsulting(dto, page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<AppApplicationResponseDTO> getById(String id, AppLanguage lang) {
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
        dto.setUniversity(universityService.getUniversityForApp(entity.getUniversityId()));
        dto.setConsulting(consultingService.getConsultingForApp(entity.getConsultingId()));
        dto.setStudent(profileService.getProfileForApp(entity.getStudentId()));
        if (entity.getConsultingStepId() != null) {
            dto.setStep(consultingStepService.getApplicationStep(entity.getConsultingStepId(), lang));
        }
        if (entity.getConsultingTariffId() != null) {
            dto.setTariff(consultingTariffService.getById(entity.getConsultingTariffId(), lang).getData());
        }
        return new ApiResponse<>(200, false, dto);
    }

    public ApiResponse<String> changeStatus(String id, AppApplicationChangeStatusDTO dto) {
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
                return new ApiResponse<>(200, false, "Success");
            } else if (dto.getStatus().equals(AppStatus.CANCELED)) {
                appApplicationRepository.statusToCanceled(id);
                return new ApiResponse<>(200, false, "Success");
            }
        }
        if (entity.getStatus().equals(AppStatus.STARTED)) { // STARTED -> FINISHED or CANCELED
            if (dto.getStatus().equals(AppStatus.FINISHED)) {
                //check where all stepLevels finished
                boolean allFinished = consultingStepLevelService.isApplicationAllStepLevelsFinished(entity.getConsultingStepId());
                if (!allFinished) {
                    log.info("Not all application {} stepLevels finished ", id);
                    return ApiResponse.forbidden("All application step levels should be closed ");
                }
                appApplicationRepository.statusToFinished(id);
                return new ApiResponse<>(200, false, "Success");
            } else if (dto.getStatus().equals(AppStatus.CANCELED)) {
                appApplicationRepository.statusToCanceled(id);
                return new ApiResponse<>(200, false, "Success");
            }
        }
        return new ApiResponse<>(400, false, "Application status can not be changed.");
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

    public AppApplicationEntity getByStudentIdAndConsultingId(String studentId, String consultingId) {
        Optional<AppApplicationEntity> appApplication = appApplicationRepository.findByStudentIdAndConsultingIdAndVisibleTrue(studentId, consultingId);
        return appApplication.orElse(null);
    }

    public AppApplicationEntity get(String id) {
        return appApplicationRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Application not Found");
            throw new ItemNotFoundException("Application not found");
        });
    }


    public ApiResponse<?> updateTariffId(AppApplicationTariffIdUpdateDTO dto, String applicationId) {
        AppApplicationEntity entity = get(applicationId);
        int num = appApplicationRepository.updateTariffId(applicationId, dto.getTariffId());
        if (num > 0) {
            return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.update"));
        }
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("fail.update"));
    }

    public ApiResponse<String> updateStep(AppApplicationStepDTO dto, String applicationId) {
        AppApplicationEntity appApplication = get(applicationId);
        // copy and save step and stepLevels
        ConsultingStepEntity applicationStep = consultingStepService.copyStepAndStepLevels(dto.getConsultingStepId(), StepType.APPLICATION);
        //update
        appApplicationRepository.updateConStepId(applicationId, applicationStep.getId());
        return new ApiResponse<>(200, false, "success");
    }


}
