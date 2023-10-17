package api.scolaro.uz.service.place;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.continent.ContinentCountryRequestDTO;
import api.scolaro.uz.dto.continent.ContinentCountryResponseDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.entity.place.ContinentCountryEntity;
import api.scolaro.uz.entity.place.CountryEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.ContinentCountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContinentCountryService {

    private final ContinentCountryRepository continentCountryRepository;

    public ApiResponse<List<CountryResponseDTO>> getList(Long continentId, AppLanguage lang) {
        List<CountryResponseDTO> dtoList = new LinkedList<>();
        Iterable<CountryEntity> all = continentCountryRepository.findByContinentId(continentId);
        if (lang.equals(AppLanguage.uz)) {
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameUz());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.ru)) {
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameRu());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.en)) {
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameEn());
                dtoList.add(dto);
            });
        }
        return new ApiResponse<>(200, false, dtoList);
    }

    public ApiResponse<ContinentCountryResponseDTO> create(ContinentCountryRequestDTO requestDTO) {
        Optional<ContinentCountryEntity> optional = continentCountryRepository.findByContinentIdAndCountryId(requestDTO.getContinentId(), requestDTO.getCountryId());
        if (optional.isPresent()){
            log.info("This union was created earlier");
            return ApiResponse.bad("This union was created earlier");
        }
        ContinentCountryEntity entity = new ContinentCountryEntity();
        entity.setContinentId(requestDTO.getContinentId());
        entity.setCountryId(requestDTO.getCountryId());
        continentCountryRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));

    }

    public ApiResponse<ContinentCountryResponseDTO> update(Long id, ContinentCountryRequestDTO dto) {
        ContinentCountryEntity entity = get(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        entity.setContinentId(dto.getContinentId());
        entity.setCountryId(dto.getCountryId());
        // update
        continentCountryRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));
    }

    public ApiResponse<Boolean> delete(Long id) {
        ContinentCountryEntity entity = get(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        int i = continentCountryRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);
    }

    public ContinentCountryEntity get(Long id) {
        return continentCountryRepository.findById(id).orElseThrow(() -> {
            log.warn("Country not found");
            return new ItemNotFoundException("Country not found");
        });
    }

    private ContinentCountryResponseDTO toDTO(ContinentCountryEntity entity) {
        ContinentCountryResponseDTO responseDTO = new ContinentCountryResponseDTO();
        responseDTO.setContinentId(entity.getContinentId());
        responseDTO.setCountryId(entity.getCountryId());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        return responseDTO;
    }
}
