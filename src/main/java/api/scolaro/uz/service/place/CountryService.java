package api.scolaro.uz.service.place;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.countryFlag.CountryRequest;
import api.scolaro.uz.dto.countryFlag.CountryResponse;
import api.scolaro.uz.entity.place.CountryEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.place.CountryRepository;
import api.scolaro.uz.service.AttachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryService {

    private final CountryRepository repository;
    private final AttachService attachService;

    public ApiResponse<String> create(CountryRequest request) {
        CountryEntity entity = repository.save(toEntity(request));
        return new ApiResponse<>("Create country flag: " + entity.getId(), 201, false);
    }

    public ApiResponse<String> update(CountryRequest request) {
        var country = repository.findById(request.id()).orElseThrow(() -> new ItemNotFoundException("Country not found " + request.id()));
        mergerCountry(country, request);
        repository.save(country);
        return new ApiResponse<>("Update country flag: " + request.id(), 200, false);
    }

    public ApiResponse<List<CountryResponse>> getAll() {
        List<CountryResponse> responseList = repository.findAllByVisibleTrue().stream()
                .map(entity -> toDTO(entity, null))
                .collect(Collectors.toList());

        return new ApiResponse<>("Country flags retrieved successfully", 200, false, responseList);
    }


    public ApiResponse<List<CountryResponse>> getAllLanguage(AppLanguage language) {
        List<CountryResponse> responseList = repository.findAllByVisibleTrue().stream()
                .map(entity -> toDTO(entity, language))
                .collect(Collectors.toList());

        return new ApiResponse<>("Country flags retrieved successfully", 200, false, responseList);
    }

    public ApiResponse<CountryResponse> getIdLanguage(Long id, AppLanguage language) {
        CountryEntity entity = get(id);
        return new ApiResponse<>("Country flag retrieved successfully", 200, false, toDTO(entity, language));
    }

    public ApiResponse<CountryResponse> getId(Long id) {
        CountryEntity entity = get(id);
        return new ApiResponse<>("Country flag retrieved successfully", 200, false, toDTO(entity, null));
    }

    public ApiResponse<String> delete(Long id) {
        CountryEntity entity = get(id);
        entity.setVisible(false);
        repository.save(entity);
        return new ApiResponse<>("Update country flag: " + id, 200, false);
    }


    private void mergerCountry(CountryEntity country, CountryRequest request) {
        country.setNameUz(request.nameUz());
        country.setNameRu(request.nameRu());
        country.setNameEn(request.nameEn());
        country.setAttachId(request.attachId());
        country.setOrderNumber(request.orderNumber());
    }

    public CountryEntity toEntity(CountryRequest request) {
        return CountryEntity.builder()
                .nameUz(request.nameUz())
                .nameRu(request.nameRu())
                .nameEn(request.nameEn())
                .attachId(request.attachId())
                .orderNumber(request.orderNumber())
                .build();
    }


    public CountryResponse toDTO(CountryEntity entity, AppLanguage language) {
        if (entity == null) return null;

        String name = null;

        if (language != null) {
            name = switch (language) {
                case uz -> entity.getNameUz();
                case ru -> entity.getNameRu();
                case en -> entity.getNameEn();
            };
        }
        return new CountryResponse(
                entity.getId(),
                language == null ? entity.getNameUz() : null,
                language == null ? entity.getNameRu() : null,
                language == null ? entity.getNameEn() : null,
                name,
                attachService.getResponseAttach(entity.getAttachId()),
                entity.getOrderNumber()
        );
    }

    public CountryEntity get(Long id) {
        return repository.findByIdAndVisibleTrue(id).orElseThrow(() ->
                new ItemNotFoundException("Country not found"));
    }

    // bu metodlar boshqa classlarda chaqirilgani uchun qoldi
    private CountryResponseDTO toDTO1(CountryEntity entity, AppLanguage language) {
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
        return toDTO1(entity, language);
    }

}
