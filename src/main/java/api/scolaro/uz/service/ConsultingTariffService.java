package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consulting.ConsultingResponseDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingIdDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffRequestDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffResponseDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffUpdateDTO;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.consulting.ConsultingTariffEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.repository.consulting.ConsultingTariffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsultingTariffService {

    private final ConsultingTariffRepository consultingTariffRepository;
    private final ConsultingService consultingService;
    private final ResourceMessageService resourceMessageService;

    public ApiResponse<?> create(ConsultingTariffRequestDTO dto) {
        ConsultingEntity consulting = consultingService.get(dto.getConsultingId());
        String userId = EntityDetails.getCurrentUserId();
        if (!userId.equals(consulting.getId())) {
            log.info("Creator and consulting Id another {}", dto);
            return new ApiResponse<>("Creator and consulting Id another", 400, true);
        }

        ConsultingTariffEntity entity = new ConsultingTariffEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setConsultingId(consulting.getId());
        entity.setTariffType(dto.getTariffType());
        entity.setStatus(dto.getStatus());
        entity.setOrder(dto.getOrder());

        consultingTariffRepository.save(entity);
        return new ApiResponse<>(200, false);
    }

    public ApiResponse<?> getById(String id, AppLanguage lang) {
        Optional<ConsultingTariffEntity> optional = consultingTariffRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("ConsultingTariff not found {}", id);
            return new ApiResponse<>("consulting not found", 400, true);
        }
        ConsultingTariffEntity entity = optional.get();
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.info("Creator and consulting Id another {}", id);
            return new ApiResponse<>("Creator and consulting Id another", 400, true);
        }
        ConsultingTariffResponseDTO dto = new ConsultingTariffResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setDescription(entity.getDescription());
        dto.setConsultingId(entity.getConsultingId());
        dto.setTariffType(entity.getTariffType());
        dto.setPrice(entity.getPrice());
        switch (lang) {
            case en -> dto.setNameEn(entity.getNameEn());
            case ru -> dto.setNameRu(entity.getNameRu());
            default -> dto.setNameUz(entity.getNameUz());
        }
        dto.setOrder(entity.getOrder());
        return new ApiResponse<>(200, false, dto);
    }

    public ApiResponse<?> update(ConsultingTariffUpdateDTO dto, String id) {
        Optional<ConsultingTariffEntity> optional = consultingTariffRepository.findByIdAndVisibleTrue(id);

        if (optional.isEmpty()) {
            log.info("ConsultingTariff not found {}", id);
            return new ApiResponse<>("consulting tariff not found", 400, true);
        }
        ConsultingTariffEntity entity = optional.get();
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.info("Creator and consulting Id another {}", id);
            return new ApiResponse<>("Creator and consulting Id another", 400, true);
        }

        entity.setId(entity.getId());
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setDescription(dto.getDescription());
        entity.setOrder(dto.getOrder());
        entity.setPrice(dto.getPrice());
        entity.setStatus(dto.getStatus());
        entity.setTariffType(dto.getTariffType());

        consultingTariffRepository.save(entity);
        return new ApiResponse<>(resourceMessageService.getMessage("success.update"), 200, false);
    }

    public ApiResponse<?> delete(String id) {
        Optional<ConsultingTariffEntity> optional = consultingTariffRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("ConsultingTariff not found {}", id);
            return new ApiResponse<>("consulting tariff not found", 400, true);
        }
        ConsultingTariffEntity entity = optional.get();
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.info("Creator and consulting Id another {}", id);
            return new ApiResponse<>("Creator and consulting Id another", 400, true);
        }
        int result = consultingTariffRepository.updateVisibleIsFalse(id, LocalDateTime.now(), EntityDetails.getCurrentUserId());
        if (result == 1) {
            return new ApiResponse<>(resourceMessageService.getMessage("success.delete"), 200, false);
        }
        return new ApiResponse<>(resourceMessageService.getMessage("fail.delete"), 200, false);
    }

    public ApiResponse<?> getByConsultingId(ConsultingIdDTO dto1, AppLanguage lang) {
        ConsultingEntity consulting = consultingService.get(dto1.getConsultingId());
        List<ConsultingTariffEntity> list = consultingTariffRepository.getByConsultingId(consulting.getId());

        List<ConsultingTariffResponseDTO> dtoList = new LinkedList<>();

        list.forEach(entity -> {
            ConsultingTariffResponseDTO dto = toDto(entity,lang);
            dtoList.add(dto);
        });
        return new ApiResponse<>(200,false,dtoList);
    }

    public ApiResponse<?> getTemplateList(AppLanguage lang) {

        List<ConsultingTariffEntity> list = consultingTariffRepository.getConsultingTariffEntitiesByTariffType();
        List<ConsultingTariffResponseDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            ConsultingTariffResponseDTO dto = toDto(entity,lang);
            dtoList.add(dto);
        });
        return new ApiResponse<>(200,false,dtoList);
    }

    public ConsultingTariffResponseDTO toDto(ConsultingTariffEntity entity, AppLanguage lang) {
        ConsultingTariffResponseDTO dto = new ConsultingTariffResponseDTO();
        switch (lang) {
            case en -> dto.setNameEn(entity.getNameEn());
            case ru -> dto.setNameRu(entity.getNameRu());
            default -> dto.setNameUz(entity.getNameUz());
        }
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setConsultingId(entity.getConsultingId());
        dto.setOrder(entity.getOrder());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }


}
