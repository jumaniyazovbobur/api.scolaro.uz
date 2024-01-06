package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusCreateDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusDTO;
import api.scolaro.uz.dto.appApplication.AppApplicationLevelStatusUpdateDTO;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;
import api.scolaro.uz.dto.transaction.request.WithdrawMoneyFromStudentDTO;
import api.scolaro.uz.entity.FeedbackEntity;
import api.scolaro.uz.entity.application.AppApplicationEntity;
import api.scolaro.uz.entity.application.AppApplicationLevelStatusEntity;
import api.scolaro.uz.entity.consulting.ConsultingStepLevelEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import api.scolaro.uz.enums.LanguageEnum;
import api.scolaro.uz.enums.StepLevelStatus;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.appApplication.AppApplicationLevelStatusRepository;
import api.scolaro.uz.repository.appApplication.AppApplicationRepository;
import api.scolaro.uz.repository.consultingStepLevel.ConsultingStepLevelRepository;
import api.scolaro.uz.service.consulting.ConsultingStepLevelService;
import api.scolaro.uz.service.transaction.TransactionService;
import api.scolaro.uz.util.TransactionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AppApplicationLevelStatusService {

    private final AppApplicationLevelStatusRepository appApplicationLevelStatusRepository;
    private final ConsultingStepLevelService consultingStepLevelService;
    private final ConsultingStepLevelRepository consultingStepLevelRepository;
    private final AppApplicationService appApplicationService;
    private final AppApplicationRepository appApplicationRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ResourceMessageService resourceMessageService;

    public ApiResponse<AppApplicationLevelStatusDTO> create(AppApplicationLevelStatusCreateDTO dto, AppLanguage lang) {
        ConsultingStepLevelEntity consultingStepLevelEntity = consultingStepLevelService.get(dto.getConsultingStepLevelId());
        // check if level finished before
        if (consultingStepLevelEntity.getStepLevelStatus() != null && consultingStepLevelEntity.getStepLevelStatus().equals(StepLevelStatus.FINISHED)) {
            return ApiResponse.bad("Tugatilgan bosqichni o'zgartirib bo'lmaydi.");
        }

        String currentConsultingId = EntityDetails.getCurrentUserDetail().getProfileConsultingId();
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
        entity.setAmount(TransactionUtil.sumToTiyin(dto.getAmount()));
        if (dto.getApplicationStepLevelStatus().equals(ApplicationStepLevelStatus.PAYMENT)) {
            entity.setApplicationStepLevelStatus(ApplicationStepLevelStatus.PAYMENT_FAILED);
        } else {
            entity.setApplicationStepLevelStatus(dto.getApplicationStepLevelStatus()); // applicationStepLevelStatus
        }
        // check if previous exists
        ConsultingStepLevelEntity previousStepLevel = consultingStepLevelRepository.getPreviousStepLevelByStepIdAndStepLevelOrderNumber(consultingStepLevelEntity.getConsultingStepId(), consultingStepLevelEntity.getOrderNumber());
        // if previous exists and it is not finished yet
        if (previousStepLevel != null && !previousStepLevel.getStepLevelStatus().equals(StepLevelStatus.FINISHED)) {
            return ApiResponse.bad(resourceMessageService.getMessage("step.before.not.finished", lang));  // if previous step not finished
        }

        appApplicationLevelStatusRepository.save(entity); // save applicationStepLevelStatus
        // payment check before start transaction if balance enough. If enough then make transaction.
        if (dto.getApplicationStepLevelStatus().equals(ApplicationStepLevelStatus.PAYMENT) &&
                profileService.checkBalance(appApplication.getStudentId(), dto.getAmount())) {
            //
            WithdrawMoneyFromStudentDTO withdrawMoney = new WithdrawMoneyFromStudentDTO();
            withdrawMoney.setApplicationId(dto.getAppApplicationId());
            withdrawMoney.setConsultingId(currentConsultingId);
            withdrawMoney.setStudentId(appApplication.getStudentId());
            withdrawMoney.setAmount(TransactionUtil.sumToTiyin(dto.getAmount())); // convert to tiyin
            withdrawMoney.setConsultingStepLevelId(dto.getConsultingStepLevelId());
            withdrawMoney.setApplicationLevelStatusId(entity.getId());

            ApiResponse<TransactionResponseDTO> apiResponse = transactionService.makeTransfer(withdrawMoney, lang); // make transfer
            if (!apiResponse.getIsError()) { // transaction success
                appApplicationLevelStatusRepository.updateApplicationStepLevelStatus(entity.getId(),
                        ApplicationStepLevelStatus.PAYMENT_SUCCESS, LocalDateTime.now());
            }
        }

        appApplicationRepository.updateConsultingStepLevelStatusId(appApplication.getId(), entity.getId());

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
                appApplicationRepository.updateConsultingStepLevelId(appApplication.getId(), nextStepLevel.getId()); // update application stepLevel id
            }
            // if next step not exists. Means it was last step and front will use separate api for finishing the application
            // api/v1/app-application/change-status/{applicationId}  - use this one TODO

        } else if (consultingStepLevelEntity.getStepLevelStatus() == null) { // if current step level not started yet! start it.
            consultingStepLevelEntity.setStartedDate(LocalDateTime.now());
            consultingStepLevelEntity.setStepLevelStatus(StepLevelStatus.IN_PROCESS);
            consultingStepLevelRepository.save(consultingStepLevelEntity); // start stepLevel
            appApplicationRepository.updateConsultingStepLevelId(appApplication.getId(), consultingStepLevelEntity.getId()); // update application stepLevel id
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

    public ApiResponse<String> levelStatusFinishPayment(String applicationLevelStatusId, AppLanguage lang) {
        AppApplicationLevelStatusEntity entity = get(applicationLevelStatusId);
        if (!entity.getApplicationStepLevelStatus().equals(ApplicationStepLevelStatus.PAYMENT_FAILED)) {
            return ApiResponse.bad(resourceMessageService.getMessage("wrong.status", lang));
        }
        AppApplicationEntity appApplication = appApplicationService.get(entity.getAppApplicationId());
        if (profileService.checkBalance(appApplication.getStudentId(), entity.getAmount())) {
            //
            WithdrawMoneyFromStudentDTO withdrawMoney = new WithdrawMoneyFromStudentDTO();
            withdrawMoney.setApplicationId(entity.getAppApplicationId());
            withdrawMoney.setConsultingId(appApplication.getConsultingId());
            withdrawMoney.setStudentId(appApplication.getStudentId());
            withdrawMoney.setAmount(entity.getAmount()); // amount is already in tiyin
            withdrawMoney.setConsultingStepLevelId(entity.getConsultingStepLevelId());
            withdrawMoney.setApplicationLevelStatusId(entity.getId());

            ApiResponse<TransactionResponseDTO> apiResponse = transactionService.makeTransfer(withdrawMoney, lang); // make transfer
            if (!apiResponse.getIsError()) { // transaction success
                appApplicationLevelStatusRepository.updateApplicationStepLevelStatus(entity.getId(),
                        ApplicationStepLevelStatus.PAYMENT_SUCCESS, LocalDateTime.now());
                return ApiResponse.bad(resourceMessageService.getMessage("success", lang));
            }
        }
        return ApiResponse.bad(resourceMessageService.getMessage("failed", lang));
    }

    public ApiResponse<List<KeyValueDTO>> getLevelStatuEnumList(AppLanguage appLanguage) {
        List<KeyValueDTO> dtoList = new LinkedList<>();
        for (ApplicationStepLevelStatus status : ApplicationStepLevelStatus.values()) {
            if (!status.equals(ApplicationStepLevelStatus.PAYMENT_FAILED) && !status.equals(ApplicationStepLevelStatus.PAYMENT_SUCCESS)) {
                dtoList.add(new KeyValueDTO(status.name(), status.getName(appLanguage)));
            }
        }
        return ApiResponse.ok(dtoList);
    }

    public AppApplicationLevelStatusDTO toDTO(AppApplicationLevelStatusEntity entity) {
        AppApplicationLevelStatusDTO dto = new AppApplicationLevelStatusDTO();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setDeadline(entity.getDeadline());
        dto.setConsultingStepLevelId(entity.getConsultingStepLevelId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setAmount(entity.getAmount());
        return dto;
    }

    public AppApplicationLevelStatusEntity get(String id) {
        return appApplicationLevelStatusRepository.findById(id).orElseThrow(() -> {
            log.warn("Application leve status not found");
            return new ItemNotFoundException("Application leve status not found");
        });
    }

}
