package api.scolaro.uz.service.place;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.destination.DestinationLanguageResponse;
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


    public ApiResponse<String> update(Long id,DestinationRequest request) {
        var country = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("Destination not found " + id));
        country.setNameUz(request.nameUz());
        country.setNameRu(request.nameRu());
        country.setNameEn(request.nameEn());
        country.setAttachId(request.attachId());
        country.setOrderNumber(request.orderNumber());
        country.setShowInMainPage(request.showInMainPage());
        repository.save(country);
        return new ApiResponse<>("Update Destination: " + id, 200, false);
    }

    public ApiResponse<List<DestinationLanguageResponse>> getAllByLanguage(AppLanguage language) {
        List<DestinationLanguageResponse> responseList = repository.findAllByVisibleTrueOrderByOrderNumber().stream()
                .map(entity -> toDTO(entity, language))
                .collect(Collectors.toList());

        return new ApiResponse<>("Destination retrieved successfully", 200, false, responseList);
    }

    public ApiResponse<DestinationResponse> getId(Long id) {
        DestinationEntity entity = get(id);
        return new ApiResponse<>("Country flag retrieved successfully", 200, false, toDTO(entity));
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
        List<DestinationResponse> responseList = pageList.stream().map(entity -> toDTO(entity)).toList();
        return new ApiResponse<>("Destination retrieved successfully", 200, false, responseList);
    }

    public DestinationEntity toEntity(DestinationRequest request) {
        return DestinationEntity.builder()
                .nameUz(request.nameUz())
                .nameRu(request.nameRu())
                .nameEn(request.nameEn())
                .attachId(request.attachId())
                .orderNumber(request.orderNumber())
                .showInMainPage(request.showInMainPage())
                .build();
    }


    public DestinationResponse toDTO(DestinationEntity entity) {
        if (entity == null) return null;

        return new DestinationResponse(
                entity.getId(),
                entity.getNameUz(),
                entity.getNameRu(),
                entity.getNameEn(),
                null,
                attachService.getResponseAttach(entity.getAttachId()),
                entity.getOrderNumber()
        );
    }

    public DestinationLanguageResponse toDTO(DestinationEntity entity, AppLanguage language) {
        if (entity == null) return null;

        String name = null;

        if (language != null) {
            name = switch (language) {
                case uz -> entity.getNameUz();
                case ru -> entity.getNameRu();
                case en -> entity.getNameEn();
            };
        }
        return new DestinationLanguageResponse(
                entity.getId(),
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
