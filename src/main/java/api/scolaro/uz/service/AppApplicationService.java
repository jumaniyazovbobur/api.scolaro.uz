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
import api.scolaro.uz.service.consulting.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AppApplicationService {

    @Autowired
    private ConsultingService consultingService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AppApplicationRepository appApplicationRepository;
    @Autowired
    private AppApplicationFilterRepository appApplicationFilterRepository;
    @Autowired
    private ConsultingStepLevelService consultingStepLevelService;
    @Autowired
    private ResourceMessageService resourceMessageService;
    @Autowired
    private ConsultingStepService consultingStepService;
    @Autowired
    private ConsultingTariffService consultingTariffService;
    @Autowired
    private ConsultingProfileService consultingProfileService;

    public ApiResponse<AppApplicationResponseDTO> create(AppApplicationRequestDTO dto) {
        String studentId = EntityDetails.getCurrentUserId();
        ConsultingEntity consulting = consultingService.get(dto.getConsultingId());

        // TODO check if application exists

        AppApplicationEntity entity = new AppApplicationEntity();
        if (dto.getUniversityId() != null) {
            AppApplicationEntity appApplication = getByStudentIdAndConsultingIdAndUniversityId(studentId, consulting.getId(), dto.getUniversityId());
            if (appApplication != null) { // application exists return this application no need to open new one
                return new ApiResponse<>(200, false, toDTO(appApplication));
            }
            UniversityEntity university = universityService.get(dto.getUniversityId()); // check university exits
            entity.setUniversityId(university.getId());
        } else { // no university
            // check for application with status trail and no university
            Optional<AppApplicationEntity> optional = appApplicationRepository.getStatusTrailApplicationWithNoUniversity(studentId, consulting.getId());
            if (optional.isPresent()) { // trail application without university exists
                return new ApiResponse<>(200, false, toDTO(optional.get()));
            }
            entity.setUniversityId(null);
        }
        // create new application
        entity.setStudentId(EntityDetails.getCurrentUserId());
        entity.setConsultingId(consulting.getId());
        entity.setStatus(AppStatus.TRAIL);
        entity.setConsultingProfileId(consulting.getManagerId());
        entity.setApplicationNumber(appApplicationRepository.getSequenceApplicationNumber()); // appliction number
        appApplicationRepository.save(entity); // save
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<Page<AppApplicationFilterMapperDTO>> filterForAdmin(AppApplicationFilterDTO filter, int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.filterForAdmin(filter, page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    /**
     * Student
     */
    public ApiResponse<Page<AppApplicationFilterMapperDTO>> getApplicationListForStudent_web(int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getApplicationListForStudent_web(EntityDetails.getCurrentUserId(), page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<Page<AppApplicationFilterMapperDTO>> getApplicationConsultingListForStudent_mobile(int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getApplicationConsultingListForStudent_mobile(page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ApiResponse<Page<AppApplicationFilterMapperDTO>> getApplicationUniversityListByConsultingIdForStudent_mobile(String consultingId, int page, int size) {
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getApplicationUniversityListByConsultingIdForStudent_mobile(consultingId, page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    /**
     * Consulting
     */
    public ApiResponse<Page<AppApplicationFilterMapperDTO>> getApplicationListForConsulting_web(AppApplicationFilterConsultingDTO dto, int page, int size) { // web
        String consultingId = EntityDetails.getCurrentUserDetail().getProfileConsultingId();
        String filterByConsultingProfileId = null;
        if (!EntityDetails.hasRoleCurrentUser(RoleEnum.ROLE_CONSULTING_MANAGER)) {
            filterByConsultingProfileId = EntityDetails.getCurrentUserId();
        }

        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getApplicationListForConsulting_web(consultingId, filterByConsultingProfileId, dto, page, size);
        Page<AppApplicationFilterMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    // returns student list by university id for which application was created. It is for consulting. Used in consulting mobile.
    public ApiResponse<Page<AppApplicationFilterMapperDTO>> getApplicationStudentListByUniversityIdForConsulting_mobile(Long universityId, AppApplicationFilterConsultingDTO filter, int page, int size, AppLanguage language) {
        String consultingId = EntityDetails.getCurrentUserDetail().getProfileConsultingId();
        String filterByConsultingProfileId = null;
        if (!EntityDetails.hasRoleCurrentUser(RoleEnum.ROLE_CONSULTING_MANAGER)) {
            filterByConsultingProfileId = EntityDetails.getCurrentUserId();
        }
        FilterResultDTO<AppApplicationFilterMapperDTO> filterResult = appApplicationFilterRepository.getApplicationStudentListForConsulting_mobile(consultingId, filterByConsultingProfileId, filter, universityId, page, size, language);
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
        String requestedUserId = EntityDetails.getCurrentUserId();
        String consultingId = EntityDetails.getCurrentUserDetail().getProfileConsultingId();

        List<String> roleList = Objects.requireNonNull(EntityDetails.getCurrentUserDetail())
                .getRoleList()
                .stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .toList();
        if (EntityDetails.hasRole(RoleEnum.ROLE_STUDENT, roleList) && !requestedUserId.equals(entity.getStudentId())) {
            log.info("Profile do not have access to this application {} profileId {}", id, requestedUserId);
            return ApiResponse.forbidden("Your access denied for this Application!");
        } else if (EntityDetails.hasRole(RoleEnum.ROLE_CONSULTING, roleList) && !consultingId.equals(entity.getConsultingId())) {
            log.info("Consulting do not have access to this application {} consulting {}", id, consultingId);
            return ApiResponse.forbidden("Your access denied for this Application!");
        }

        AppApplicationResponseDTO dto = new AppApplicationResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        if (entity.getUniversityId() != null) {
            dto.setUniversity(universityService.getUniversityForApp(entity.getUniversityId()));
        }
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
        String consultingId = EntityDetails.getCurrentUserDetail().getProfileConsultingId();
        if (!consultingId.equals(entity.getConsultingId())) {
            log.info("Consulting {}  do not have access to application {}", consultingId, id);
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
        dto.setId(entity.getId());
        dto.setConsultingId(entity.getConsultingId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUniversityId(entity.getUniversityId());
        dto.setStudentId(entity.getStudentId());
        dto.setStatus(entity.getStatus());
        dto.setApplicationNumber(entity.getApplicationNumber());
        return dto;
    }

    public AppApplicationEntity getByStudentIdAndConsultingId(String studentId, String consultingId) { // TODO
        Optional<AppApplicationEntity> appApplication = appApplicationRepository.findByStudentIdAndConsultingIdAndVisibleTrue(studentId, consultingId);
        return appApplication.orElse(null);
    }

    public AppApplicationEntity getByStudentIdAndConsultingIdAndUniversityId(String studentId, String consultingId, Long universityId) {
        Optional<AppApplicationEntity> appApplication = appApplicationRepository.findByStudentIdAndConsultingIdAndUniversityIdAndVisibleTrue(studentId, consultingId, universityId);
        return appApplication.orElse(null);
    }

    public AppApplicationEntity get(String id) {
        return appApplicationRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Application not Found");
            return new ItemNotFoundException("Application not found");
        });
    }

    public AppApplicationEntity getFetchAllData(String id) {
        return appApplicationRepository.findAllDataByIdAndVisibleIsTrue(id).orElseThrow(() -> {
            log.warn("Application not Found");
            return new ItemNotFoundException("Application not found");
        });
    }


    public ApiResponse<?> updateTariffId(AppApplicationTariffIdUpdateDTO dto, String applicationId) {
        AppApplicationEntity entity = get(applicationId);
        String consultingId = EntityDetails.getCurrentUserDetail().getProfileConsultingId();
        if (!consultingId.equals(entity.getConsultingId())) {
            log.info("Consulting {}  do not have access to application {}", consultingId, applicationId);
            return ApiResponse.forbidden("Your access denied for this Application!");
        }
        int num = appApplicationRepository.updateTariffId(applicationId, dto.getTariffId());
        if (num > 0) {
            return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.update"));
        }
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("fail.update"));
    }

    public ApiResponse<String> updateStep(AppApplicationStepDTO dto, String applicationId) {
        AppApplicationEntity appApplication = get(applicationId);
        String consultingId = Objects.requireNonNull(EntityDetails.getCurrentUserDetail()).getProfileConsultingId();
        if (!consultingId.equals(appApplication.getConsultingId())) {
            log.info("Consulting {}  do not have access to application {}", consultingId, applicationId);
            return ApiResponse.forbidden("Your access denied for this Application!");
        }
        // copy and save step and stepLevels
        ConsultingStepEntity applicationStep = consultingStepService.copyStepAndStepLevels(dto.getConsultingStepId(), StepType.APPLICATION);
        //update
        appApplicationRepository.updateConStepId(applicationId, applicationStep.getId());
        return new ApiResponse<>(200, false, "success");
    }


    public ApiResponse<String> updateConsultingProfile(String applicationId, String newProfileId) {
        AppApplicationEntity application = get(applicationId);
        String consultingId = Objects.requireNonNull(EntityDetails.getCurrentUserDetail()).getProfileConsultingId();

        if (!application.getConsultingId().equals(consultingId)) {
            log.warn("Access denied updateConsultingProfile inDbConsultingId={},currentConsultingId={}", application.getConsultingId(), consultingId);
            return ApiResponse.forbidden("Access denied");
        }
        appApplicationRepository.updateApplicationConsultingProfile(applicationId, newProfileId);
        return ApiResponse.ok();
    }

    public Object updateUniversity(String applicationId, String newUniversityId) {
        AppApplicationEntity application = get(applicationId);
        String consultingId = Objects.requireNonNull(EntityDetails.getCurrentUserDetail()).getProfileConsultingId();

        if (!application.getConsultingId().equals(consultingId)) {
            log.warn("Access denied updateConsultingProfile inDbConsultingId={},currentConsultingId={}", application.getConsultingId(), consultingId);
            return ApiResponse.forbidden("Access denied");
        }
        appApplicationRepository.updateApplicationConsultingProfile(applicationId, newUniversityId);
        return ApiResponse.ok();
    }
}
