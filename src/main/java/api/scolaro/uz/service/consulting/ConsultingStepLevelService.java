package api.scolaro.uz.service.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelCreateDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelResponseDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelUpdateDTO;
import api.scolaro.uz.entity.consulting.ConsultingStepLevelEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.StepLevelType;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consultingStepLevel.ConsultingStepLevelRepository;
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
public class ConsultingStepLevelService {
    private final ConsultingStepLevelRepository consultingStepLevelRepository;
    private final ResourceMessageService resourceMessageService;


    public ApiResponse<String> create(ConsultingStepLevelCreateDTO dto) {
        ConsultingStepLevelEntity stepEntity = new ConsultingStepLevelEntity();
        stepEntity.setNameUz(dto.getNameUz());
        stepEntity.setNameEn(dto.getNameEn());
        stepEntity.setNameRu(dto.getNameRu());
        stepEntity.setStepLevelType(StepLevelType.CONSULTING);
        stepEntity.setDescription(dto.getDescription());
        stepEntity.setOrderNumber(dto.getOrderNumber());
        stepEntity.setConsultingStepId(dto.getConsultingStepId());
        stepEntity.setConsultingId(EntityDetails.getCurrentUserId()); // set consulting id
        consultingStepLevelRepository.save(stepEntity);
        return new ApiResponse<>(200, false, resourceMessageService.getMessage("success.insert"));
    }

    public ApiResponse<ConsultingStepLevelDTO> update(String id, ConsultingStepLevelUpdateDTO dto) {
        ConsultingStepLevelEntity entity = get(id);
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.warn("consultingStepLevelEntity {} not belongs to current consulting {} ", id, EntityDetails.getCurrentUserId());
            throw new AppBadRequestException("ConsultingStepLevel not belongs to current consulting.");
        }
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setDescription(dto.getDescription());
        entity.setOrderNumber(dto.getOrderNumber());
        // update
        consultingStepLevelRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<Boolean> delete(String id) {
        ConsultingStepLevelEntity entity = get(id);
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.warn("consultingStepLevelEntity {} not belongs to current consulting {} ", id, EntityDetails.getCurrentUserId());
            throw new AppBadRequestException("ConsultingStepLevel not belongs to current consulting.");
        }
        int i = consultingStepLevelRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);
    }

    public ApiResponse<ConsultingStepLevelDTO> getById(String id) {
        return new ApiResponse<>(200, false, toDTO(get(id)));
    }

    public List<ConsultingStepLevelDTO> getConsultingStepLevelListByConsultingStepId(String consultingStepId) {
        List<ConsultingStepLevelEntity> entityList = consultingStepLevelRepository.getAllByConsultingStepId(consultingStepId);
        List<ConsultingStepLevelDTO> dtoList = new LinkedList<>();
        for (ConsultingStepLevelEntity entity : entityList) {
            ConsultingStepLevelDTO dto = new ConsultingStepLevelDTO();
            dto.setId(entity.getId());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setDescription(entity.getDescription());
            dto.setOrderNumber(entity.getOrderNumber());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean isApplicationAllStepLevelsFinished(String applicationStepId) {
        Long count = consultingStepLevelRepository.getApplicationNotFinishedStepLevelCount(applicationStepId);
        return count == 0;
    }

    public ConsultingStepLevelEntity get(String id) {
        return consultingStepLevelRepository.findById(id).orElseThrow(() -> {
            log.warn("ConsultingStepLevel not found");
            return new ItemNotFoundException("ConsultingStepLevel not found");
        });
    }

    private ConsultingStepLevelDTO toDTO(ConsultingStepLevelEntity entity) {
        ConsultingStepLevelDTO dto = new ConsultingStepLevelDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumber(entity.getOrderNumber());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public void createForApp(List<ConsultingStepLevelEntity> list, String stepId) {
        for (ConsultingStepLevelEntity entity : list) {
            ConsultingStepLevelEntity stepEntity = new ConsultingStepLevelEntity();

            stepEntity.setNameUz(entity.getNameUz());
            stepEntity.setNameEn(entity.getNameEn());
            stepEntity.setNameRu(entity.getNameRu());
            stepEntity.setStepLevelType(entity.getStepLevelType());
            stepEntity.setDescription(entity.getDescription());
            stepEntity.setOrderNumber(entity.getOrderNumber());
            stepEntity.setConsultingStepId(stepId);
            stepEntity.setConsultingId(entity.getConsultingId()); // set consulting id

            consultingStepLevelRepository.save(stepEntity);
        }
    }

    public ConsultingStepLevelResponseDTO getByIdForApp(String id, AppLanguage lang) {
        ConsultingStepLevelEntity entity = get(id);
        ConsultingStepLevelResponseDTO dto = new ConsultingStepLevelResponseDTO();
        switch (lang) {
            case ru -> dto.setName(entity.getNameRu());
            case en -> dto.setName(entity.getNameEn());
            default -> dto.setName(entity.getNameUz());
        }

        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setConsultingStepId(entity.getConsultingStepId());
        dto.setOrderNumber(entity.getOrderNumber());
        return dto;
    }
}
