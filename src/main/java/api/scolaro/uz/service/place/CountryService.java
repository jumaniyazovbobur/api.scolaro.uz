package api.scolaro.uz.service.place;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.*;
import api.scolaro.uz.entity.place.CountryEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.place.CountryRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {

    private final CountryRepository countryRepository;

    public ApiResponse<CountryResponseDTO> countryCreate(CountryRequestDTO countryDTO) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCreatedId(EntityDetails.getCurrentUserId());
        countryEntity.setNameEn(countryDTO.getNameEn());
        countryEntity.setNameUz(countryDTO.getNameUz());
        countryEntity.setNameRu(countryDTO.getNameRu());
        countryRepository.save(countryEntity);
        return new ApiResponse<>(200, false, toDTO(countryEntity));
    }

    public ApiResponse<List<CountryResponseDTO>> getList(AppLanguage lang) {
        List<CountryResponseDTO> dtoList = new LinkedList<>();
        if (lang.equals(AppLanguage.uz)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleTrueOrderByNameUzAsc();
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameUz());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.ru)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleTrueOrderByNameRuAsc();
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameRu());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.en)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleTrueOrderByNameEnAsc();
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameEn());
                dtoList.add(dto);
            });
        }
        return new ApiResponse<>(200, false, dtoList);
    }


    public CountryPaginationDTO pagination(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");
        Page<CountryEntity> all = countryRepository.getAllByVisibleTrue(pageable);
        long totalElements = all.getTotalElements();
        int totalPages = all.getTotalPages();
        List<CountryResponseDTO> dtoList = all.stream().map(this::toDTO).toList();
        return new CountryPaginationDTO(totalElements, totalPages, dtoList);
    }

    public ApiResponse<CountryResponseDTO> update(Long id, CountryRequestDTO dto) {
        CountryEntity entity = get(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        // update
        countryRepository.save(entity);
        return new ApiResponse<>(200, false, toDTO(entity));

    }

    public ApiResponse<Boolean> delete(Long id) {
        CountryEntity entity = get(id);
        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }
        int i = countryRepository.deleted(id, EntityDetails.getCurrentUserId(), LocalDateTime.now());
        return new ApiResponse<>(200, false, i > 0);

    }

    public CountryEntity get(Long id) {
        return countryRepository.findById(id).orElseThrow(() -> {
            log.warn("Country not found");
            return new ItemNotFoundException("Country not found");
        });
    }

    public ApiResponse<List<CountryResponseDTO>> getCountryListWithUniversity(AppLanguage lang) {
        List<CountryResponseDTO> dtoList = new LinkedList<>();
        if (lang.equals(AppLanguage.uz)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleTrueOrderByNameUzAsc();
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameUz());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.ru)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleTrueOrderByNameRuAsc();
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameRu());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.en)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleTrueOrderByNameEnAsc();
            all.forEach(country -> {
                CountryResponseDTO dto = new CountryResponseDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameEn());
                dtoList.add(dto);
            });
        }
        return new ApiResponse<>(200, false, dtoList);
    }


    public List<CountryResponseDTO> search(String query, AppLanguage language) {
        List<CountryEntity> entityList = countryRepository.searchByName(query.toLowerCase());
        List<CountryResponseDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> dtoList.add(toDTO(entity, language)));
        return dtoList;
    }

    private CountryResponseDTO toDTO(CountryEntity entity) {
        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }

    private CountryResponseDTO toDTO(CountryEntity entity, AppLanguage language) {
        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setId(entity.getId());
        switch (language) {
            case en -> dto.setName(entity.getNameEn());
            case uz -> dto.setName(entity.getNameUz());
            case ru -> dto.setName(entity.getNameRu());
        }
        return dto;
    }

    public CountryResponseDTO getById(Long id, AppLanguage language) {
        CountryEntity entity = get(id);
        return toDTO(entity, language);
    }

}
