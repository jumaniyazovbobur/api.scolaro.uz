package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.university.UniversityCreateDTO;
import api.scolaro.uz.dto.university.UniversityFilterDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.dto.university.UniversityUpdateDTO;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.university.UniversityCustomRepository;
import api.scolaro.uz.repository.university.UniversityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Slf4j
@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityCustomRepository customRepository;
    private final AttachService attachService;
    private final UniversityDegreeService universityDegreeService;


    public ApiResponse<UniversityResponseDTO> create(UniversityCreateDTO dto) {
        UniversityEntity entity = new UniversityEntity();
        entity.setName(dto.getName());
        entity.setRating(dto.getRating());
        entity.setWebSite(dto.getWebSite());
        entity.setCountryId(dto.getCountryId());
        entity.setPhotoId(dto.getPhotoId());
        entity.setDescription(dto.getDescription());
        entity.setCreatedId(EntityDetails.getCurrentUserId());
        universityRepository.save(entity);
        universityDegreeService.merger(entity.getId(), dto.getDegreeList()); //merge university degreeType
        return ApiResponse.ok(toDTO(entity));
    }

    public ApiResponse<?> deleted(Long id) {
        UniversityEntity entity = get(id);
        int result = universityRepository.deleted(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<UniversityResponseDTO> update(Long id, UniversityUpdateDTO dto) {
        UniversityEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setWebSite(dto.getWebSite());
        entity.setRating(dto.getRating());
        entity.setCountryId(dto.getCountryId());
        entity.setDescription(dto.getDescription());
        universityRepository.save(entity);
        universityDegreeService.merger(entity.getId(), dto.getDegreeList()); //merge university degreeType
        return ApiResponse.ok(toDTO(entity));
    }

    public ApiResponse<UniversityResponseDTO> getById(Long id) {
        UniversityEntity entity = get(id);
        UniversityResponseDTO responseDTO = toDTO(entity);
        responseDTO.setDegreeTypeList(universityDegreeService.getUniversityDegreeTypeList(id));
        return ApiResponse.ok(responseDTO);
    }

    public ApiResponse<UniversityResponseDTO> getByIdDetail(Long id, AppLanguage appLanguage) {
        UniversityEntity entity = get(id);
        UniversityResponseDTO responseDTO = toDTO(entity);
        responseDTO.setDegreeList(universityDegreeService.getUniversityDegreeTypeList(id, appLanguage));
        return ApiResponse.ok(responseDTO);
    }


    public PageImpl<UniversityResponseDTO> filter(int page, int size, UniversityFilterDTO dto) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<UniversityEntity> universityList = customRepository.filterPagination(dto, page, size);
        return new PageImpl<>(universityList.getContent().stream().map(this::toDTO).toList(), pageable,
                universityList.getTotalElement());
    }

    private UniversityResponseDTO toDTO(UniversityEntity entity) {
        UniversityResponseDTO dto = new UniversityResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRating(entity.getRating());
        dto.setCountryId(entity.getCountryId());
        dto.setWebSite(entity.getWebSite());
        if (entity.getPhotoId() != null){
            dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        }
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public UniversityEntity get(Long id) {
        return universityRepository.findById(id).orElseThrow(() -> {
            log.warn("university not fount {}", id);
            return new ItemNotFoundException("University not found");
        });

    }


    public UniversityResponseDTO getUniversityForApp(Long id) {
        UniversityEntity entity = get(id);
        UniversityResponseDTO dto = new UniversityResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        return dto;
    }
}
