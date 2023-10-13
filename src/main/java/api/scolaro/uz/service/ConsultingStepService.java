package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingStep.ConsultingStepCreateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateDTO;
import api.scolaro.uz.dto.consultingStep.ConsultingStepUpdateResponseDTO;
import api.scolaro.uz.entity.ConsultingStepEntity;
import api.scolaro.uz.enums.StepType;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consultinStep.ConsultingStepRepository;
import api.scolaro.uz.repository.consulting.ConsultingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultingStepService {
    private final ConsultingStepRepository consultingStepRepository;
    private final ConsultingRepository consultingRepository;

    public ApiResponse<?> create(ConsultingStepCreateDTO dto) {
        ConsultingStepEntity stepEntity = new ConsultingStepEntity();
        getConsulting(dto.getConsultingId());

        stepEntity.setNameUz(dto.getNameUz());
        stepEntity.setNameEn(dto.getNameEn());
        stepEntity.setNameRu(dto.getNameRu());
        stepEntity.setStepType(StepType.CONSULTING);
        stepEntity.setDescription(dto.getDescription());
        stepEntity.setOrderNumbers(dto.getOrderNumber());
        stepEntity.setConsultingId(dto.getConsultingId());
        consultingStepRepository.save(stepEntity);
        return new ApiResponse<>(200, false);
    }

    public ApiResponse<Boolean> delete(String id) {
        ConsultingStepEntity entity = get(id);
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())){
            log.warn("Consulting author is not incorrect or not found {}" , entity.getConsultingId());
            throw new ItemNotFoundException("Consulting author is not incorrect or not found");
        }
        int i = consultingStepRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);
    }

    public ConsultingStepEntity get(String id) {
        return consultingStepRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Consulting Step not found");
            return new ItemNotFoundException("Consulting Step not found");
        });
    }

    public void getConsulting(String id) {
        consultingRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Consulting not found");
            return new ItemNotFoundException("Consulting not found");
        });
    }

    public ApiResponse<ConsultingStepUpdateResponseDTO> update(String id, ConsultingStepUpdateDTO dto) {
        ConsultingStepEntity entity = get(id);

        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumbers(dto.getOrderNumbers());
        entity.setDescription(dto.getDescription());
        // update
        consultingStepRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));

    }

    private ConsultingStepUpdateResponseDTO toDTO(ConsultingStepEntity entity) {
        ConsultingStepUpdateResponseDTO dto = new ConsultingStepUpdateResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumbers(entity.getOrderNumbers());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
