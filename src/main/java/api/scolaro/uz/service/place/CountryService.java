package api.scolaro.uz.service.place;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.country.CountryLanguageResponse;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.country.CountryRequest;
import api.scolaro.uz.dto.country.CountryResponse;
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
        return new ApiResponse<>("Create country : " + entity.getId(), 201, false);
    }

    public ApiResponse<String> update(CountryRequest request) {
        var country = repository.findById(request.id()).orElseThrow(() -> new ItemNotFoundException("Country not found " + request.id()));
        country.setNameUz(request.nameUz());
        country.setNameRu(request.nameRu());
        country.setNameEn(request.nameEn());
        country.setAttachId(request.attachId());
        country.setOrderNumber(request.orderNumber());
        repository.save(country);
        return new ApiResponse<>("Update country : " + request.id(), 200, false);
    }

    public ApiResponse<List<CountryResponse>> allForAdmin() {
        List<CountryResponse> responseList = repository.findAllByVisibleTrueOrderByOrderNumberAsc().stream()
                .map(entity -> toDTO(entity))
                .collect(Collectors.toList());

        return new ApiResponse<>("Country  retrieved successfully", 200, false, responseList);
    }


    public ApiResponse<List<CountryResponseDTO>> getAllByLanguage(AppLanguage language) {
        // TODO Program bor yoki qaysidir consulting xizmat ko'rsatadigan country-ni return qilamiz barchasini emas
        List<CountryResponseDTO> responseList = repository.findAllByVisibleTrueOrderByOrderNumberAsc().stream()
                .map(entity -> toDTO(entity, language))
                .collect(Collectors.toList());

        return new ApiResponse<>("Country retrieved successfully", 200, false, responseList);
    }


    public ApiResponse<CountryResponse> getId(Long id) {
        CountryEntity entity = get(id);
        return new ApiResponse<>("Country  retrieved successfully", 200, false, toDTO(entity));
    }

    public ApiResponse<String> delete(Long id) {
        CountryEntity entity = get(id);
        entity.setVisible(false);
        repository.save(entity);
        return new ApiResponse<>("Delete country : " + id, 200, false);
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

    public CountryResponse toDTO(CountryEntity entity) {
        if (entity == null) return null;

        return new CountryResponse(
                entity.getId(),
                entity.getNameUz(),
                entity.getNameRu(),
                entity.getNameEn(),
                null,
                attachService.getResponseAttach(entity.getAttachId()),
                entity.getOrderNumber()
        );
    }


    public CountryResponseDTO toDTO(CountryEntity entity, AppLanguage language) {
        if (entity == null) return null;

        String name = null;

        if (language != null) {
            name = switch (language) {
                case uz -> entity.getNameUz();
                case ru -> entity.getNameRu();
                case en -> entity.getNameEn();
            };
        }
        CountryResponseDTO response = new CountryResponseDTO();
        response.setId(entity.getId());
        response.setName(name);
        response.setAttach(attachService.getResponseAttach(entity.getAttachId()));
        response.setOrderNumber(entity.getOrderNumber());

        return response;
    }

    public CountryEntity get(Long id) {
        return repository.findByIdAndVisibleTrue(id).orElseThrow(() ->
                new ItemNotFoundException("Country not found"));
    }

    public CountryResponseDTO getById(Long id, AppLanguage language) {
        CountryEntity entity = get(id);
        return toDTO(entity, language);
    }

}
