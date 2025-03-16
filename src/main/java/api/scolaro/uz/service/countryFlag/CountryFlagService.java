package api.scolaro.uz.service.countryFlag;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.countryFlag.CountryFlagRequest;
import api.scolaro.uz.dto.countryFlag.CountryFlagResponse;
import api.scolaro.uz.entity.place.CountryFlagEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.countryFlag.CountryFlagRepository;
import api.scolaro.uz.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryFlagService {

    private final CountryFlagRepository repository;
    private final AttachService attachService;

    public ApiResponse<String> create(CountryFlagRequest request) {
        CountryFlagEntity entity = repository.save(toEntity(request));
        return new ApiResponse<>("Create country flag: " + entity.getId(), 201, false);
    }

    public ApiResponse<String> update(CountryFlagRequest request) {
        var countryFlag = repository.findById(request.id()).orElseThrow(() -> new ItemNotFoundException("CountryFlag not found " + request.id()));
        mergerCountryFlag(countryFlag, request);
        repository.save(countryFlag);
        return new ApiResponse<>("Update country flag: " + request.id(), 200, false);
    }

    public ApiResponse<List<CountryFlagResponse>> getAll() {
        List<CountryFlagResponse> responseList = repository.findAllByVisibleTrue().stream()
                .map(entity -> toDTO(entity, null))
                .collect(Collectors.toList());

        return new ApiResponse<>("Country flags retrieved successfully", 200, false, responseList);
    }


    public ApiResponse<List<CountryFlagResponse>> getAllLanguage(AppLanguage language) {
        List<CountryFlagResponse> responseList = repository.findAllByVisibleTrue().stream()
                .map(entity -> toDTO(entity, language))
                .collect(Collectors.toList());

        return new ApiResponse<>("Country flags retrieved successfully", 200, false, responseList);
    }

    public ApiResponse<CountryFlagResponse> getIdLanguage(String id, AppLanguage language) {
        CountryFlagEntity entity = get(id);
        return new ApiResponse<>("Country flag retrieved successfully", 200, false, toDTO(entity, language));
    }

    public ApiResponse<CountryFlagResponse> getId(String id) {
        CountryFlagEntity entity = get(id);
        return new ApiResponse<>("Country flag retrieved successfully", 200, false, toDTO(entity, null));
    }

    public ApiResponse<String> delete(String id) {
        CountryFlagEntity entity = get(id);
        entity.setVisible(false);
        repository.save(entity);
        return new ApiResponse<>("Update country flag: " + id, 200, false);
    }


    private void mergerCountryFlag(CountryFlagEntity countryFlag, CountryFlagRequest request) {
        countryFlag.setNameUz(request.nameUz());
        countryFlag.setNameRu(request.nameRu());
        countryFlag.setAttachId(request.attachId());
        countryFlag.setOrderNumber(request.orderNumber());
    }

    public CountryFlagEntity toEntity(CountryFlagRequest request) {
        return CountryFlagEntity.builder()
                .nameUz(request.nameUz())
                .nameRu(request.nameRu())
                .nameEn(request.nameEn())
                .attachId(request.attachId())
                .orderNumber(request.orderNumber())
                .build();
    }


    public CountryFlagResponse toDTO(CountryFlagEntity entity, AppLanguage language) {
        if (entity == null) return null;

        String name = null;

        if (language != null) {
            name = switch (language) {
                case uz -> entity.getNameUz();
                case ru -> entity.getNameRu();
                case en -> entity.getNameEn();
            };
        }
        return new CountryFlagResponse(
                entity.getId(),
                language == null ? entity.getNameUz() : null,
                language == null ? entity.getNameRu() : null,
                language == null ? entity.getNameEn() : null,
                name,
                attachService.getResponseAttach(entity.getAttachId()),
                entity.getOrderNumber()
        );
    }

    public CountryFlagEntity get(String id) {
        return repository.findByIdAndVisibleTrue(id).orElseThrow(() ->
                new ItemNotFoundException("Country not found"));
    }


}
