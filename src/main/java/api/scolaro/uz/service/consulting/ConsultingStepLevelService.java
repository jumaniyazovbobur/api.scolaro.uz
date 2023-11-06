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
import api.scolaro.uz.mapper.ConsultingStepLevelMapper;
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

    public List<ConsultingStepLevelDTO> getConsultingStepLevelListByConsultingStepId(String consultingStepId, AppLanguage language) {
        List<ConsultingStepLevelEntity> entityList = consultingStepLevelRepository.getAllByConsultingStepId(consultingStepId);
        List<ConsultingStepLevelDTO> dtoList = new LinkedList<>();
        for (ConsultingStepLevelEntity entity : entityList) {
            ConsultingStepLevelDTO dto = new ConsultingStepLevelDTO();
            dto.setId(entity.getId());
            switch (language) {
                case uz -> dto.setName(entity.getNameUz());
                case en -> dto.setName(entity.getNameEn());
                default -> dto.setName(entity.getNameRu());
            }
            dto.setDescription(entity.getDescription());
            dto.setOrderNumber(entity.getOrderNumber());
            dtoList.add(dto);
        }
        return dtoList;
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

    public List<ConsultingStepLevelDTO> getApplicationStepLevelList(String consultingStepId, AppLanguage lang) {
        List<ConsultingStepLevelMapper> entityList = consultingStepLevelRepository.getApplicationStepLevelList(consultingStepId, lang.name());
        List<ConsultingStepLevelDTO> dtoList = new LinkedList<>();
        for (ConsultingStepLevelMapper mapper : entityList) {
            ConsultingStepLevelDTO dto = new ConsultingStepLevelDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setDescription(mapper.getDescription());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setStartDate(mapper.getStartedDate());
            dto.setFinishDate(mapper.getFinishedDate());
            dto.setStatus(mapper.getStepLevelStatus());
            dto.setLevelStatusList(mapper.getLevelStatusList()); // level status list
            dto.setLevelAttachList(mapper.getLevelAttachList()); // level attach list
            dtoList.add(dto);
        }
        return dtoList;
    }

    public void copyStepLevels(String fromStepId, String toStepId, String consultingId) {
        List<ConsultingStepLevelEntity> stepLevelEntityList = consultingStepLevelRepository.getAllByConsultingStepId(fromStepId);
        for (ConsultingStepLevelEntity entity : stepLevelEntityList) {
            ConsultingStepLevelEntity stepEntity = new ConsultingStepLevelEntity();
            stepEntity.setNameUz(entity.getNameUz());
            stepEntity.setNameEn(entity.getNameEn());
            stepEntity.setNameRu(entity.getNameRu());
            stepEntity.setOrderNumber(entity.getOrderNumber());
            stepEntity.setDescription(entity.getDescription());
            stepEntity.setConsultingStepId(toStepId);
            stepEntity.setConsultingId(consultingId); // set consulting id
            consultingStepLevelRepository.save(stepEntity);
        }
    }
}
