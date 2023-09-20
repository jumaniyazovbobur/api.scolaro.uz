package api.scolaro.uz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.dachatop.dto.country.*;
import uz.dachatop.dto.response.ApiResponse;
import uz.dachatop.entity.CountryEntity;
import uz.dachatop.enums.AppLanguage;
import uz.dachatop.exp.AppBadRequestException;
import uz.dachatop.exp.ItemNotFoundException;
import uz.dachatop.mapper.CountryMapper;
import uz.dachatop.repository.CountryRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {

    private final CountryRepository countryRepository;

    public ApiResponse<CountryResponseDTO> countryCreate(CountryRequestDTO countryDTO) {
        /*Optional<CountryEntity> optional = countryRepository.findByIdAndVisibleTrue(countryDTO.getId());
        if (optional.isPresent()) {
            log.warn("Country Already exist");
            throw new ItemAlreadyExistsException("Country Already exist");
        }*/
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setNameEn(countryDTO.getNameEn());
        countryEntity.setNameUz(countryDTO.getNameUz());
        countryEntity.setNameRu(countryDTO.getNameRu());
        countryRepository.save(countryEntity);
        return new ApiResponse<>(200, false, toDTO(countryEntity));
    }

    public ApiResponse<List<CountryDTO>> getList(AppLanguage lang) {
        List<CountryDTO> dtoList = new LinkedList<>();
        if (lang.equals(AppLanguage.uz)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleOrderByNameUzAsc(true);
            all.forEach(country -> {
                CountryDTO dto = new CountryDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameUz());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.ru)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleOrderByNameRuAsc(true);
            all.forEach(country -> {
                CountryDTO dto = new CountryDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameRu());
                dtoList.add(dto);
            });
        } else if (lang.equals(AppLanguage.en)) {
            Iterable<CountryEntity> all = countryRepository.findAllByVisibleOrderByNameEnAsc(true);
            all.forEach(country -> {
                CountryDTO dto = new CountryDTO();
                dto.setId(country.getId());
                dto.setName(country.getNameEn());
                dtoList.add(dto);
            });
        }
        return new ApiResponse<>(200, false, dtoList);
    }


    private CountryDTO addToListWithSwitch(CountryDTO dto, CountryEntity country, AppLanguage lang) {

        dto.setId(country.getId());
        switch (lang) {
            case uz:
                dto.setName(country.getNameUz());
                break;
            case en:
                dto.setName(country.getNameEn());
                break;
            case ru:
                dto.setName(country.getNameRu());
                break;
        }
        return dto;
    }


    public ApiResponse<CountryResponseDTO> getById(Long id, AppLanguage language) {
        CountryMapper mapper = countryRepository.getCountryByKey(id).orElseThrow(() -> {
            log.warn("Country not found");
            return new ItemNotFoundException("Country not found");
        });

        return new ApiResponse<>(200, false, toDTO(mapper, language));

    }

    public CountryPaginationDTO pagination(int page, int size) {

        PageRequest pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");

        Page<CountryEntity> all = countryRepository.getAllByVisibleTrue(pageable);

        long totalElements = all.getTotalElements();
        int totalPages = all.getTotalPages();

        List<CountryResponseDTO> dtoList = all.stream().map(this::toDTO).toList();
        return new CountryPaginationDTO(totalElements, totalPages, dtoList);

    }


    public ApiResponse<CountryResponseDTO> update(Long id, CountryUpdateDTO dto) {
        CountryEntity entity = get(id);

        if (entity.getVisible().equals(Boolean.FALSE)) {
            log.warn("Is visible false");
            throw new AppBadRequestException("Is visible false");
        }

        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());

        countryRepository.save(entity);

        return new ApiResponse<>(200, false, toDTO(entity));

    }

    public ApiResponse<Boolean> delete(Long id) {

        int i = countryRepository.deleteStatus(false, id);

        return new ApiResponse<>(200, false, i > 0);

    }

    public CountryEntity get(Long id) {
        return countryRepository.findById(id).orElseThrow(() -> {
            log.warn("Country not found");
            throw new ItemNotFoundException("Country not found");
        });
    }


    private CountryResponseDTO toDTO(CountryMapper mapper, AppLanguage lang) {
        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setId(mapper.getCou_id());

        switch (lang) {
            case ru:
                dto.setName(mapper.getNameRu());
                break;
            case en:
                dto.setName(mapper.getNameEn());
                break;
            case uz:
                dto.setName(mapper.getNameUz());
                break;
        }
        return dto;

    }

    private CountryResponseDTO toDTO(CountryEntity entity) {
        CountryResponseDTO dto = new CountryResponseDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameRu(entity.getNameRu());
        dto.setNameEn(entity.getNameEn());
        return dto;
    }

}
