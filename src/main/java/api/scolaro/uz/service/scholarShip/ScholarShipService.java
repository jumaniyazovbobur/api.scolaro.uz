package api.scolaro.uz.service.scholarShip;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipFilterDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipRequestDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipResponseDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipUpdateDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.scholarShip.ScholarShipEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.mapper.AppApplicationFilterMapperDTO;
import api.scolaro.uz.mapper.ScholarShipMapperDTO;
import api.scolaro.uz.repository.scholarShip.ScholarShipFilterRepository;
import api.scolaro.uz.repository.scholarShip.ScholarShipRepository;
import api.scolaro.uz.service.AttachService;
import api.scolaro.uz.service.ProfileService;
import api.scolaro.uz.service.ResourceMessageService;
import api.scolaro.uz.service.UniversityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScholarShipService {

    private final ScholarShipRepository scholarShipRepository;
    private final ResourceMessageService resourceMessageService;
    private final AttachService attachService;
    private final ScholarShipFilterRepository scholarShipFilterRepository;
    private final ScholarShipDegreeService scholarShipDegreeService;
    private final UniversityService universityService;

    public ApiResponse<?> create(ScholarShipRequestDTO dto) {
        UniversityEntity university = universityService.get(dto.getUniversityId());

        ScholarShipEntity entity = new ScholarShipEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPhotoId(dto.getPhotoId());
        entity.setStartDate(dto.getStartDate());
        entity.setExpiredDate(dto.getExpiredDate());
        entity.setPrice(dto.getPrice());
        entity.setUniversityId(dto.getUniversityId());
        entity.setCreatorId(EntityDetails.getCurrentUserId());
        scholarShipRepository.save(entity);
        scholarShipDegreeService.merger(entity.getId(), dto.getDegreeTypeList());
        return new ApiResponse<>(resourceMessageService.getMessage("success.insert"), 200, false);
    }

    public ApiResponse<?> getById(String id, AppLanguage language) {
        ScholarShipEntity entity = get(id);
        return new ApiResponse<>(200, false, toDTO(entity, language));
    }

    public ApiResponse<?> getByIdDetail(String id, AppLanguage language) {
        ScholarShipEntity entity = get(id);
        ScholarShipResponseDTO shipResponse = toDTO(entity, language);
        UniversityResponseDTO university = universityService.getByIdDetailResponse(entity.getUniversityId(), language);
        shipResponse.setUniversity(university);
        return new ApiResponse<>(200, false, shipResponse);
    }


    public ApiResponse<?> update(String id, ScholarShipRequestDTO dto, AppLanguage language) {
        ScholarShipEntity entity = get(id);
        entity.setName(entity.getName());
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setExpiredDate(dto.getExpiredDate());
        entity.setPrice(dto.getPrice());
        entity.setUniversityId(dto.getUniversityId());
        if (dto.getPhotoId() != null) {
            entity.setPhotoId(dto.getPhotoId());
        }
        scholarShipRepository.save(entity);
        scholarShipDegreeService.merger(entity.getId(), dto.getDegreeTypeList());
        return new ApiResponse<>(200, false, toDTO(entity, language));
    }

    public ApiResponse<?> delete(String id) {
        int result = scholarShipRepository.updateDeletedDateAndVisible(id, LocalDateTime.now());
        if (result == 1) {
            return new ApiResponse<>(resourceMessageService.getMessage("success.delete"), 200, false);
        }
        return new ApiResponse<>(resourceMessageService.getMessage("fail.delete"), 200, false);
    }


    public ApiResponse<Page<ScholarShipMapperDTO>> filter(ScholarShipFilterDTO dto, Integer page, Integer size, AppLanguage language) {
        FilterResultDTO<ScholarShipMapperDTO> filterResult = scholarShipFilterRepository.filter(dto, page, size, language);
        Page<ScholarShipMapperDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }

    public ScholarShipEntity get(String id) {
        Optional<ScholarShipEntity> optional = scholarShipRepository.findByIdAndVisibleTrue(id);
        if (optional.isEmpty()) {
            log.info("ScholarShip not found {} ", id);
            throw new ItemNotFoundException("ScholarShip not found" + id);
        }
        return optional.get();
    }


    public ApiResponse<?> getTopGrant(AppLanguage language) {
        List<ScholarShipEntity> list = scholarShipRepository.getTopScholarShip();
        List<ScholarShipResponseDTO> dtoList = new LinkedList<>();
        for (ScholarShipEntity entity : list) {
            ScholarShipResponseDTO dto = toDTO(entity, language);
            dto.setUniversity(universityService.toDTO(entity.getUniversity()));
            dtoList.add(dto);
        }
        return new ApiResponse<>(200, false, dtoList);
    }

    public ScholarShipResponseDTO toDTO(ScholarShipEntity entity, AppLanguage language) {
        ScholarShipResponseDTO dto = new ScholarShipResponseDTO();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setStartDate(entity.getStartDate());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUniversityId(entity.getUniversityId());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setDegreeTypeList(scholarShipDegreeService.getScholarShipDegreeTypeList(entity.getId(), language));
        dto.setCreatedDate(entity.getCreatedDate());
        if (entity.getPhotoId() != null) {
            dto.setAttach(attachService.getResponseAttach(entity.getPhotoId()));
        }
        return dto;
    }
}
