package api.scolaro.uz.service.consulting;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffRequestDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffResponseDTO;
import api.scolaro.uz.dto.consultingTariff.ConsultingTariffUpdateDTO;
import api.scolaro.uz.entity.consulting.ConsultingTariffEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.consulting.ConsultingTariffRepository;
import api.scolaro.uz.service.ResourceMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsultingTariffService {

    private final ConsultingTariffRepository consultingTariffRepository;
    private final ResourceMessageService resourceMessageService;

    public ApiResponse<String> create(ConsultingTariffRequestDTO dto) {
        ConsultingTariffEntity entity = new ConsultingTariffEntity();
        entity.setName(dto.getName());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setDescriptionRu(dto.getDescriptionRu());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setPrice(dto.getPrice());
        entity.setConsultingId(EntityDetails.getCurrentUserId());
        entity.setTariffType(dto.getTariffType());
        entity.setStatus(dto.getStatus());
        entity.setOrder(dto.getOrder());
        consultingTariffRepository.save(entity);
        return new ApiResponse<>(200, false,resourceMessageService.getMessage("success.insert"));
    }

    public ApiResponse<ConsultingTariffResponseDTO> getById(String id, AppLanguage lang) {
        ConsultingTariffEntity entity = get(id);
        ConsultingTariffResponseDTO dto = new ConsultingTariffResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setName(entity.getName());
        dto.setConsultingId(entity.getConsultingId());
        dto.setTariffType(entity.getTariffType());
        dto.setPrice(entity.getPrice());
        switch (lang) {
            case en -> dto.setDescription(entity.getDescriptionEn());
            case ru -> dto.setDescription(entity.getDescriptionRu());
            default -> dto.setDescription(entity.getDescriptionUz());
        }
        dto.setOrder(entity.getOrder());
        return new ApiResponse<>(200, false, dto);
    }

    public ApiResponse<String> update(ConsultingTariffUpdateDTO dto, String id) {
        ConsultingTariffEntity entity = get(id);
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.warn("Author is not incorrect or not found {}", entity.getConsultingId());
            throw new ItemNotFoundException("Author is not incorrect or not found");
        }
        entity.setId(entity.getId());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setDescriptionRu(dto.getDescriptionRu());
        entity.setName(dto.getName());
        entity.setOrder(dto.getOrder());
        entity.setPrice(dto.getPrice());
        entity.setStatus(dto.getStatus());
        entity.setTariffType(dto.getTariffType());
        consultingTariffRepository.save(entity);
        return new ApiResponse<>(resourceMessageService.getMessage("success.update"), 200, false);
    }

    public ApiResponse<String> delete(String id) {
        ConsultingTariffEntity entity = get(id);
        if (!entity.getConsultingId().equals(EntityDetails.getCurrentUserId())) {
            log.warn("Author is not incorrect or not found {}", entity.getConsultingId());
            throw new ItemNotFoundException("Author is not incorrect or not found");
        }
        int result = consultingTariffRepository.updateVisibleIsFalse(id, LocalDateTime.now(), EntityDetails.getCurrentUserId());
        if (result == 1) {
            return new ApiResponse<>(resourceMessageService.getMessage("success.delete"), 200, false);
        }
        return new ApiResponse<>(resourceMessageService.getMessage("fail.delete"), 200, false);
    }

    public ApiResponse<List<ConsultingTariffResponseDTO>> getAllByConsultingId(String consultingId, AppLanguage lang) {
        List<ConsultingTariffEntity> list = consultingTariffRepository.getByConsultingId(consultingId);
        List<ConsultingTariffResponseDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> dtoList.add(toDto(entity, lang)));
        return new ApiResponse<>(200, false, dtoList);
    }

    public ApiResponse<List<ConsultingTariffResponseDTO>> getTemplateList(AppLanguage lang) {
        List<ConsultingTariffEntity> list = consultingTariffRepository.getConsultingTariffEntitiesByTariffType();
        List<ConsultingTariffResponseDTO> dtoList = new LinkedList<>();
        list.forEach(entity -> {
            ConsultingTariffResponseDTO dto = toDto(entity, lang);
            dtoList.add(dto);
        });
        return new ApiResponse<>(200, false, dtoList);
    }

    public ConsultingTariffResponseDTO toDto(ConsultingTariffEntity entity, AppLanguage lang) {
        ConsultingTariffResponseDTO dto = new ConsultingTariffResponseDTO();
        switch (lang) {
            case en -> dto.setDescription(entity.getDescriptionEn());
            case ru -> dto.setDescriptionRu(entity.getDescriptionRu());
            default -> dto.setDescriptionUz(entity.getDescriptionUz());
        }
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setConsultingId(entity.getConsultingId());
        dto.setOrder(entity.getOrder());
        dto.setPrice(entity.getPrice());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public ConsultingTariffEntity get(String id) {
        return consultingTariffRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            log.warn("Consulting tariff not found");
            return new ItemNotFoundException("Consulting tariff not found");
        });
    }
}
