package api.scolaro.uz.service.place;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.continent.ContinentDTO;
import api.scolaro.uz.dto.continent.ContinentRequestDTO;
import api.scolaro.uz.dto.continent.ContinentResponseDTO;
import api.scolaro.uz.entity.place.ContinentEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.ContinentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContinentService {

    private final ContinentRepository continentRepository;

    public ApiResponse<List<ContinentResponseDTO>> getList(AppLanguage lang) {
        List<ContinentResponseDTO> dtoList = new LinkedList<>();
        Iterable<ContinentEntity> all = continentRepository.findAllByVisibleTrueOrderByOrderNumber();

        if (lang.equals(AppLanguage.uz)) {
            all.forEach(country -> {
                ContinentResponseDTO dto = new ContinentResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameUz());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.ru)) {
            all.forEach(country -> {
                ContinentResponseDTO dto = new ContinentResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameRu());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.en)) {
            all.forEach(country -> {
                ContinentResponseDTO dto = new ContinentResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameEn());
                dtoList.add(dto);
            });
        }
        return new ApiResponse<>(200, false, dtoList);
    }

    public ApiResponse<ContinentDTO> continentCreate(ContinentRequestDTO continentDTO) {
        ContinentEntity continentEntity = new ContinentEntity();
        continentEntity.setCreatedId(EntityDetails.getCurrentUserId());
        continentEntity.setNameEn(continentDTO.getNameEn());
        continentEntity.setNameUz(continentDTO.getNameUz());
        continentEntity.setNameRu(continentDTO.getNameRu());
        continentEntity.setOrderNumber(continentDTO.getOrder());
        continentRepository.save(continentEntity);
        return new ApiResponse<>(200, false, toDTO(continentEntity));
    }

    public ApiResponse<ContinentDTO> update(Long id, ContinentRequestDTO dto) {
        ContinentEntity entity = get(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrder());
        // update
        continentRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<Boolean> delete(Long id) {
        ContinentEntity entity = get(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        int i = continentRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);
    }

    public ContinentEntity get(Long id) {
        return continentRepository.findById(id).orElseThrow(() -> {
            log.warn("Country not found");
            return new ItemNotFoundException("Country not found");
        });
    }

    private ContinentDTO toDTO(ContinentEntity entity) {
        ContinentDTO continentDTO = new ContinentDTO();
        continentDTO.setNameUz(entity.getNameUz());
        continentDTO.setNameEn(entity.getNameEn());
        continentDTO.setNameRu(entity.getNameRu());
        continentDTO.setOrder(entity.getOrderNumber());
        continentDTO.setCreatedDate(entity.getCreatedDate());
        return continentDTO;
    }

    public ApiResponse<ContinentDTO> getById(Long id) {
        ContinentEntity entity = get(id);
        return ApiResponse.ok(toDTO(entity));
    }
}
