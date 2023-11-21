package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusCreateDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusUpdateDTO;
import api.scolaro.uz.entity.FeedbackEntity;
import api.scolaro.uz.entity.application.AppApplicationEntity;
import api.scolaro.uz.entity.application.AppApplicationLevelStatusEntity;
import api.scolaro.uz.entity.consulting.ConsultingStepLevelEntity;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import api.scolaro.uz.enums.StepLevelStatus;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.appApplication.AppApplicationLevelStatusRepository;
import api.scolaro.uz.repository.consultingStepLevel.ConsultingStepLevelRepository;
import api.scolaro.uz.service.consulting.ConsultingStepLevelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class AppApplicationLevelStatusService {

    private final AppApplicationLevelStatusRepository appApplicationLevelStatusRepository;
    private final ConsultingStepLevelService consultingStepLevelService;
    private final ConsultingStepLevelRepository consultingStepLevelRepository;
    private final AppApplicationService appApplicationService;

    public ApiResponse<AppApplicationLevelStatusDTO> create(AppApplicationLevelStatusCreateDTO dto) {
        ConsultingStepLevelEntity consultingStepLevelEntity = consultingStepLevelService.get(dto.getConsultingStepLevelId());
        // check if level finished before
        if (consultingStepLevelEntity.getStepLevelStatus() != null && consultingStepLevelEntity.getStepLevelStatus().equals(StepLevelStatus.FINISHED)) {
            return ApiResponse.bad("Tugatilgan bosqichni o'zgartirib bo'lmaydi.");
        }

        String currentConsultingId = EntityDetails.getCurrentUserId();
        // check if application belongs to current consulting.
        AppApplicationEntity appApplication = appApplicationService.get(dto.getAppApplicationId());
        if (!appApplication.getConsultingId().equals(currentConsultingId)) { //
            log.info("Consulting {}  do not have access to application {}", currentConsultingId, dto.getAppApplicationId());
            return ApiResponse.forbidden("Your access denied for this Application!");
        }

        AppApplicationLevelStatusEntity entity = new AppApplicationLevelStatusEntity();
        entity.setDescription(dto.getDescription());
        entity.setDeadline(dto.getDeadline());
        entity.setAppApplicationId(dto.getAppApplicationId());// application
        entity.setConsultingStepLevelId(dto.getConsultingStepLevelId()); // stepLevel
        entity.setApplicationStepLevelStatus(dto.getApplicationStepLevelStatus()); // applicationStepLevelStatus

        // check if previous exists
        ConsultingStepLevelEntity previousStepLevel = consultingStepLevelRepository.getPreviousStepLevelByStepIdAndStepLevelOrderNumber(consultingStepLevelEntity.getConsultingStepId(), consultingStepLevelEntity.getOrderNumber());
        // if previous exists and it is not finished yet
        if (previousStepLevel != null && !previousStepLevel.getStepLevelStatus().equals(StepLevelStatus.FINISHED)) {
            return ApiResponse.bad("Oldingi bosqich tugatilmagan."); // if previous step not finished
        }

        appApplicationLevelStatusRepository.save(entity); // save applicationStepLevelStatus

        // if stepLevel going to finish. update ConsultingStepLevelEntity
        if (dto.getApplicationStepLevelStatus().equals(ApplicationStepLevelStatus.STEP_LEVEL_FINISHED)) {
            consultingStepLevelEntity.setFinishedDate(LocalDateTime.now());
            consultingStepLevelEntity.setStepLevelStatus(StepLevelStatus.FINISHED);
            consultingStepLevelRepository.save(consultingStepLevelEntity); // finish stepLevel

            // start next stepLevel
            ConsultingStepLevelEntity nextStepLevel = consultingStepLevelRepository.getNextStepLevelByStepIdAndStepLevelOrderNumber(consultingStepLevelEntity.getConsultingStepId(), consultingStepLevelEntity.getOrderNumber());
            if (nextStepLevel != null) { // if next step exists
                nextStepLevel.setStartedDate(LocalDateTime.now());
                nextStepLevel.setStepLevelStatus(StepLevelStatus.IN_PROCESS);
                consultingStepLevelRepository.save(nextStepLevel); // start stepLevel
            }
            // if next step not exists. Means it was last step and front will use separate api for finishing the application
            // api/v1/app-application/change-status/{applicationId}  - use this one

        } else if (consultingStepLevelEntity.getStepLevelStatus() == null) { // if current step level not started yet! start it.
            consultingStepLevelEntity.setStartedDate(LocalDateTime.now());
            consultingStepLevelEntity.setStepLevelStatus(StepLevelStatus.IN_PROCESS);
            consultingStepLevelRepository.save(consultingStepLevelEntity); // start stepLevel
        }

        return ApiResponse.ok(toDTO(entity));
    }

    public ApiResponse<AppApplicationLevelStatusDTO> update(String levelStatusId, AppApplicationLevelStatusUpdateDTO dto) {
        AppApplicationLevelStatusEntity entity = get(levelStatusId);
        entity.setDeadline(dto.getDeadline());
        entity.setDescription(dto.getDescription());
        appApplicationLevelStatusRepository.save(entity);
        return ApiResponse.ok(toDTO(entity));
    }

    public AppApplicationLevelStatusDTO toDTO(AppApplicationLevelStatusEntity entity) {
        AppApplicationLevelStatusDTO dto = new AppApplicationLevelStatusDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setDeadline(entity.getDeadline());
        dto.setConsultingStepLevelId(entity.getConsultingStepLevelId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public AppApplicationLevelStatusEntity get(String id) {
        return appApplicationLevelStatusRepository.findById(id).orElseThrow(() -> {
            log.warn("Application leve status not found");
            return new ItemNotFoundException("Application leve status not found");
        });
    }

}