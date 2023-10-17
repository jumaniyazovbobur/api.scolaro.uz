package api.scolaro.uz.service.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingStep.ConsultingStepCreateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateDTO;
import api.scolaro.uz.entity.consulting.ConsultingStepEntity;
import api.scolaro.uz.enums.StepType;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consultinStep.ConsultingStepRepository;
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

    public ApiResponse<?> create(ConsultingStepCreateDTO dto) {
        ConsultingStepEntity stepEntity = new ConsultingStepEntity();
        stepEntity.setName(dto.getName());
        stepEntity.setStepType(StepType.CONSULTING);
        stepEntity.setDescription(dto.getDescription());
        stepEntity.setOrderNumber(dto.getOrderNumber());
        stepEntity.setConsultingId(EntityDetails.getCurrentUserId()); // set consultingId
        consultingStepRepository.save(stepEntity);
        return new ApiResponse<>(200, false);
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

    public ApiResponse<ConsultingStepDTO> update(String id, ConsultingStepUpdateDTO dto) {
        ConsultingStepEntity entity = get(id);
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.warn("consultingStepEntity {} not belongs to consulting {} ", id, EntityDetails.getCurrentUserId());
            throw new AppBadRequestException("ConsultingStep not belongs to current consulting.");
        }
        entity.setName(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setDescription(dto.getDescription());
        // update
        consultingStepRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<ConsultingStepDTO> getConsultingDetail(String id) {
        ConsultingStepEntity entity = get(id);
        ConsultingStepDTO consultingStepDTO = toDTO(entity);
        // set consultingStepLevel
        consultingStepDTO.setLevelList(consultingStepLevelService.getConsultingStepLevelListByConsultingStepId(id));
        return new ApiResponse<>(200, false, consultingStepDTO);
    }

    public ApiResponse<ConsultingStepDTO> getConsultingStepListByRequestedConsulting() {
        List<ConsultingStepEntity> entityList = consultingStepRepository.getAllByConsultingId(EntityDetails.getCurrentUserId());
        List<ConsultingStepDTO> dtoList = new LinkedList<>();
        entityList.forEach(consultingStepEntity -> dtoList.add(toDTO(consultingStepEntity)));
        return null;
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
}
