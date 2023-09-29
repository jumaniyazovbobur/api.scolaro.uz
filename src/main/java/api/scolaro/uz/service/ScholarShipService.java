package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.scholarShip.ScholarShipRequestDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipResponseDTO;
import api.scolaro.uz.dto.scholarShip.ScholarShipUpdateDTO;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.ScholarShipEntity;
import api.scolaro.uz.repository.scholarShip.ScholarShipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScholarShipService {

    private final ScholarShipRepository scholarShipRepository;
    private final ResourceMessageService resourceMessageService;
    private final ProfileService profileService;
    private final AttachService attachService;

    public ApiResponse<?> create(ScholarShipRequestDTO dto) {
        log.info("ScholarShip create {}", dto);
        ProfileEntity profile = profileService.get(EntityDetails.getCurrentUserId());

        ScholarShipEntity entity = new ScholarShipEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDegreeType(dto.getDegreeType());
        entity.setPhotoId(dto.getPhotoId());
        entity.setExpiredDate(dto.getExpiredDate());
        entity.setCreatorId(profile.getId());
        scholarShipRepository.save(entity);
        return new ApiResponse<>(resourceMessageService.getMessage("success.insert"), 200, false);
    }

    public ApiResponse<?> getById(String id) {
        Optional<ScholarShipEntity> optional = scholarShipRepository.findById(id);
        if (optional.isEmpty()) {
            log.info("ScholarShip not found {}", id);
            return new ApiResponse<>(resourceMessageService.getMessage("scholarShip.not-found"), 400, true);
        }
        ScholarShipEntity entity = optional.get();
        ScholarShipResponseDTO dto = new ScholarShipResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setDescription(entity.getDescription());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setDegreeType(entity.getDegreeType());
        if (entity.getPhotoId() != null) {
            dto.setAttach(attachService.getResponseAttach(entity.getPhotoId()));
        }
        return new ApiResponse<>(200, false, dto);
    }

    public ApiResponse<?> update(String id, ScholarShipUpdateDTO dto) {
        Optional<ScholarShipEntity> optional = scholarShipRepository.findById(id);
        if (optional.isEmpty()) {
            log.info("ScholarShip not found {}", id);
            return new ApiResponse<>(resourceMessageService.getMessage("scholarShip.not-found"), 400, true);
        }
        ScholarShipEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDegreeType(dto.getDegreeType());
        entity.setExpiredDate(dto.getExpiredDate());
        if (dto.getAttach() != null) {
            entity.setPhotoId(dto.getAttach().getId());
        }

        scholarShipRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<?> delete(String id){
        Optional<ScholarShipEntity> optional = scholarShipRepository.findById(id);
        if (optional.isEmpty()) {
            log.info("ScholarShip not found {}", id);
            return new ApiResponse<>(resourceMessageService.getMessage("scholarShip.not-found"), 400, true);
        }
        scholarShipRepository.updateDeletedDateAndVisible(id, LocalDateTime.now());
        return new ApiResponse<>(resourceMessageService.getMessage("success.delete"), 200, false);
    }

    public ScholarShipResponseDTO toDTO(ScholarShipEntity entity) {
        ScholarShipResponseDTO dto = new ScholarShipResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setDescription(entity.getDescription());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setDegreeType(entity.getDegreeType());
        if (entity.getPhotoId() != null) {
            dto.setAttach(attachService.getResponseAttach(entity.getPhotoId()));
        }
        return dto;
    }


}
