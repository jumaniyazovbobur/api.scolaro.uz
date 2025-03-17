package api.scolaro.uz.service.place;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.destination.DestinationRequest;
import api.scolaro.uz.dto.destination.DestinationResponse;
import api.scolaro.uz.entity.place.DestinationEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.place.DestinationRepository;
import api.scolaro.uz.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinationService {
    private final DestinationRepository repository;
    private final AttachService attachService;

    public ApiResponse<String> create(DestinationRequest request) {
        DestinationEntity entity = repository.save(toEntity(request));
        return new ApiResponse<>("Create country flag: " + entity.getId(), 201, false);
    }


    public ApiResponse<String> update(DestinationRequest request) {
        var country = repository.findById(request.id()).orElseThrow(() -> new ItemNotFoundException("Destination not found " + request.id()));
        mergerCountry(country, request);
        repository.save(country);
        return new ApiResponse<>("Update Destination: " + request.id(), 200, false);
    }

    public ApiResponse<List<DestinationResponse>> getAll() {
        List<DestinationResponse> responseList = repository.findAllByVisibleTrueOrderByOrderNumber().stream()
                .map(entity -> toDTO(entity, null))
                .collect(Collectors.toList());

        return new ApiResponse<>("Destination retrieved successfully", 200, false, responseList);
    }

    public ApiResponse<List<DestinationResponse>> getAllLanguage(AppLanguage language) {
        List<DestinationResponse> responseList = repository.findAllByVisibleTrueOrderByOrderNumber().stream()
                .map(entity -> toDTO(entity, language))
                .collect(Collectors.toList());

        return new ApiResponse<>("Destination retrieved successfully", 200, false, responseList);
    }

    public ApiResponse<DestinationResponse> getIdLanguage(Long id, AppLanguage language) {
        DestinationEntity entity = get(id);
        return new ApiResponse<>("Country flag retrieved successfully", 200, false, toDTO(entity, language));
    }

    public ApiResponse<DestinationResponse> getId(Long id) {
        DestinationEntity entity = get(id);
        return new ApiResponse<>("Country flag retrieved successfully", 200, false, toDTO(entity, null));
    }

    public ApiResponse<String> delete(Long id) {
        DestinationEntity entity = get(id);
        entity.setVisible(false);
        repository.save(entity);
        return new ApiResponse<>("Delete country flag: " + id, 200, false);
    }

    public ApiResponse<List<DestinationResponse>> filter(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DestinationEntity> pageList = repository.findAllByVisibleTrueOrderByOrderNumber(pageable);
        List<DestinationResponse> responseList = pageList.stream().map(entity -> toDTO(entity, null)).toList();
        return new ApiResponse<>("Destination retrieved successfully", 200, false, responseList);
    }


    private void mergerCountry(DestinationEntity country, DestinationRequest request) {
        country.setNameUz(request.nameUz());
        country.setNameRu(request.nameRu());
        country.setNameEn(request.nameEn());
        country.setAttachId(request.attachId());
        country.setOrderNumber(request.orderNumber());
    }

    public DestinationEntity toEntity(DestinationRequest request) {
        return DestinationEntity.builder()
                .nameUz(request.nameUz())
                .nameRu(request.nameRu())
                .nameEn(request.nameEn())
                .attachId(request.attachId())
                .orderNumber(request.orderNumber())
                .build();
    }


    public DestinationResponse toDTO(DestinationEntity entity, AppLanguage language) {
        if (entity == null) return null;

        String name = null;

        if (language != null) {
            name = switch (language) {
                case uz -> entity.getNameUz();
                case ru -> entity.getNameRu();
                case en -> entity.getNameEn();
            };
        }
        return new DestinationResponse(
                entity.getId(),
                language == null ? entity.getNameUz() : null,
                language == null ? entity.getNameRu() : null,
                language == null ? entity.getNameEn() : null,
                name,
                attachService.getResponseAttach(entity.getAttachId()),
                entity.getOrderNumber()
        );
    }

    public DestinationEntity get(Long id) {
        return repository.findByIdAndVisibleTrue(id).orElseThrow(() ->
                new ItemNotFoundException("Destination not found"));
    }


}
