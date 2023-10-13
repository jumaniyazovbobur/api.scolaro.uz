package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelCreateDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelUpdateDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelUpdateResponseDTO;
import api.scolaro.uz.entity.ConsultingStepLevelEntity;
import api.scolaro.uz.enums.StepLevelType;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consultinStep.ConsultingStepRepository;
import api.scolaro.uz.repository.consultingStepLevel.ConsultingStepLevelRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
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
    private final ConsultingStepRepository consultingStepRepository;
    private final ProfileRepository profileRepository;

    public ApiResponse<?> create(ConsultingStepLevelCreateDTO dto) {
        ConsultingStepLevelEntity stepEntity = new ConsultingStepLevelEntity();
        getConsultingStep(dto.getConsultingStepId());
        getProfile(dto.getPrtId());

        stepEntity.setNameUz(dto.getNameUz());
        stepEntity.setNameEn(dto.getNameEn());
        stepEntity.setNameRu(dto.getNameRu());
        stepEntity.setStepLevelType(StepLevelType.CONSULTING);
        stepEntity.setDescription(dto.getDescription());
        stepEntity.setOrderNumbers(dto.getOrderNumbers());
        stepEntity.setConsultingStepId(dto.getConsultingStepId());
        stepEntity.setPrtId(dto.getPrtId());
        consultingStepLevelRepository.save(stepEntity);
        return new ApiResponse<>(200, false);
    }

    public ApiResponse<Boolean> delete(String id) {
        ConsultingStepLevelEntity entity = getConsultingStepLevel(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        int i = consultingStepLevelRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);
    }

    public ConsultingStepLevelEntity getConsultingStepLevel(String id) {
        return consultingStepLevelRepository.findById(id).orElseThrow(() -> {
            log.warn("Country not found");
            return new ItemNotFoundException("Country not found");
        });
    }

    public void getProfile(String id) {
        profileRepository.findById(id).orElseThrow(() -> {
            log.warn("Profile not found");
            return new ItemNotFoundException("Profile not found");
        });
    }

    public void getConsultingStep(String id) {
        consultingStepRepository.findById(id).orElseThrow(() -> {
            log.warn("Consulting Step not found");
            return new ItemNotFoundException("Country not found");
        });
    }

    public ApiResponse<ConsultingStepLevelUpdateResponseDTO> update(String id, ConsultingStepLevelUpdateDTO dto) {
        ConsultingStepLevelEntity entity = getConsultingStepLevel(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumbers(dto.getOrderNumbers());
        entity.setDescription(dto.getDescription());
        // update
        consultingStepLevelRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));

    }

    private ConsultingStepLevelUpdateResponseDTO toDTO(ConsultingStepLevelEntity entity) {
        ConsultingStepLevelUpdateResponseDTO dto = new ConsultingStepLevelUpdateResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        dto.setOrderNumbers(entity.getOrderNumbers());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public ApiResponse<List<ConsultingStepLevelDTO>> getByConsultingId(String id) {
        List<ConsultingStepLevelEntity> entity = consultingStepLevelRepository.getByConsultingStepId(id);
        List<ConsultingStepLevelDTO> dto = new LinkedList<>();
        for (ConsultingStepLevelEntity consultingStepLevel : entity) {
            if (consultingStepLevel == null) {
                log.warn("Entity is null");
                throw new AppBadRequestException("Entity is null");
            }
            if (consultingStepLevel.getVisible().equals(Boolean.FALSE)) {
                log.warn("Is visible false");
                throw new AppBadRequestException("Is visible false");
            }

            ConsultingStepLevelDTO consultingStepLevelDTO = new ConsultingStepLevelDTO();
            consultingStepLevelDTO.setId(consultingStepLevel.getId());
            consultingStepLevelDTO.setNameUz(consultingStepLevel.getNameUz());
            consultingStepLevelDTO.setNameRu(consultingStepLevel.getNameRu());
            consultingStepLevelDTO.setNameEn(consultingStepLevel.getNameEn());
            consultingStepLevelDTO.setDescription(consultingStepLevel.getDescription());
            consultingStepLevelDTO.setConsultingStepId(consultingStepLevel.getConsultingStepId());
            consultingStepLevelDTO.setOrderNumbers(consultingStepLevel.getOrderNumbers());
            consultingStepLevelDTO.setPrtId(consultingStepLevel.getPrtId());
            dto.add(consultingStepLevelDTO);
        }


        return new ApiResponse<>(200, false, dto);
    }
}
