package api.scolaro.uz.service.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingStep.ConsultingStepCreateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepResponseDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffResponseDTO;
import api.scolaro.uz.entity.consulting.ConsultingStepEntity;
import api.scolaro.uz.entity.consulting.ConsultingTariffEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.StepType;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consultinStep.ConsultingStepRepository;
import api.scolaro.uz.service.ResourceMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultingStepService {
    private final ConsultingStepRepository consultingStepRepository;
    private final ConsultingStepLevelService consultingStepLevelService;
    private final ResourceMessageService resourceMessageService;

    public ApiResponse<String> create(ConsultingStepCreateDTO dto) {
        ConsultingStepEntity stepEntity = new ConsultingStepEntity();
        stepEntity.setName(dto.getName());
        stepEntity.setStepType(StepType.CONSULTING);
        stepEntity.setDescription(dto.getDescription());
        stepEntity.setOrderNumber(dto.getOrderNumber());
        stepEntity.setConsultingId(EntityDetails.getCurrentUserId()); // set consultingId
        consultingStepRepository.save(stepEntity);
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.insert"));
    }

    public ApiResponse<Boolean> delete(String id) {
        ConsultingStepEntity entity = get(id);
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.warn("Consulting author is not incorrect or not found {}", entity.getConsultingId());
            throw new ItemNotFoundException("Consulting author is not incorrect or not found");
        }
        int i = consultingStepRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);
    }

    public ApiResponse<ConsultingStepDTO> getConsultingById(String id) {
        ConsultingStepEntity entity = get(id);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<ConsultingStepDTO> update(String id, ConsultingStepCreateDTO dto) {
        ConsultingStepEntity entity = get(id);
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.warn("consultingStepEntity {} not belongs to consulting {} ", id, EntityDetails.getCurrentUserId());
            throw new AppBadRequestException("ConsultingStep not belongs to current consulting.");
        }
        entity.setName(dto.getName());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setDescription(dto.getDescription());
        // update
        consultingStepRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<ConsultingStepDTO> getConsultingDetail(String id, AppLanguage language) {
        ConsultingStepEntity entity = get(id);
        ConsultingStepDTO consultingStepDTO = toDTO(entity);
        // set consultingStepLevel
        consultingStepDTO.setLevelList(consultingStepLevelService.getConsultingStepLevelListByConsultingStepId(id, language));
        return new ApiResponse<>(200, false, consultingStepDTO);
    }

    public ApiResponse<List<ConsultingStepDTO>> getConsultingStepListByRequestedConsulting() {
        List<ConsultingStepEntity> entityList = consultingStepRepository.getAllByConsultingId(EntityDetails.getCurrentUserId());
        List<ConsultingStepDTO> dtoList = new LinkedList<>();
        entityList.forEach(consultingStepEntity -> dtoList.add(toDTO(consultingStepEntity)));
        return new ApiResponse<>(200, false, dtoList);
    }

    public ConsultingStepEntity get(String id) {
        return consultingStepRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Consulting Step not found");
            return new ItemNotFoundException("Consulting Step not found");
        });
    }

    private ConsultingStepDTO toDTO(ConsultingStepEntity entity) {
        ConsultingStepDTO dto = new ConsultingStepDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setDescription(entity.getDescription());
        return dto;
    }


    //    private ConsultingStepCreateDTO toDTOForApp(ConsultingStepEntity entity) {
//        ConsultingStepCreateDTO dto = new ConsultingStepCreateDTO();
//        dto.setName(entity.getName());
//        dto.setOrderNumber(entity.getOrderNumber());
//        dto.setDescription(entity.getDescription());
//        return dto;
//    }
    public String createForApp(ConsultingStepEntity step) {
        ConsultingStepEntity entity = new ConsultingStepEntity();

        entity.setName(step.getName());
        entity.setOrderNumber(step.getOrderNumber());
        entity.setDescription(step.getDescription());
        entity.setConsultingId(step.getConsultingId());
        entity.setStepType(StepType.APPLICATION);

        consultingStepRepository.save(entity);
        return entity.getId();
    }

    public ConsultingStepResponseDTO getApplicationStep(String consultingStepId, AppLanguage lang) {
        ConsultingStepEntity entity = get(consultingStepId);
        ConsultingStepResponseDTO dto = new ConsultingStepResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getStepType());
        dto.setDescription(entity.getDescription());
        dto.setLevelList(consultingStepLevelService.getApplicationStepLevelList(consultingStepId, lang));
        return dto;
    }

    public ConsultingStepEntity copyStep(String fromStepId, String consultingId, StepType stepType) {
        ConsultingStepEntity stepEntity = get(fromStepId);
        //copy consulting step
        ConsultingStepEntity applicationStep = new ConsultingStepEntity();
        applicationStep.setName(stepEntity.getName());
        applicationStep.setStepType(stepType);
        applicationStep.setOrderNumber(1);
        applicationStep.setDescription(stepEntity.getDescription());
        applicationStep.setConsultingId(EntityDetails.getCurrentUserId());
        applicationStep.setConsultingId(consultingId);
        // save
        consultingStepRepository.save(applicationStep);
        return applicationStep;
    }

    public ConsultingStepEntity copyStepAndStepLevels(String fromStepId, StepType stepType) {
        String consultingId = EntityDetails.getCurrentUserId();
        ConsultingStepEntity consultingStepEntity = copyStep(fromStepId, consultingId, stepType);
        // copy and save consultingStepLevel
        consultingStepLevelService.copyStepLevels(fromStepId, consultingStepEntity.getId(), consultingId);
        return consultingStepEntity;
    }


    public ApiResponse<?> copyTemplateToConsultingStep(String templateStepId) {
        ConsultingStepEntity templateStepEntity = get(templateStepId);
        if (!templateStepEntity.getStepType().equals(StepType.TEMPLATE)) {
            log.warn("Only template steps allowed to copy.");
            throw new AppBadRequestException("Only template steps allowed to copy.");
        }
        copyStepAndStepLevels(templateStepId, StepType.CONSULTING);
        return ApiResponse.ok();
    }

    public ApiResponse<List<ConsultingStepDTO>> getTemplateList() {
        List<ConsultingStepEntity> list = consultingStepRepository.getConsultingStepTemlateTariffList();
        List<ConsultingStepDTO> dtoList = new LinkedList<>();
        list.forEach(consultingStepEntity -> dtoList.add(toDTO(consultingStepEntity)));
        return new ApiResponse<>(200, false, dtoList);
    }
}
