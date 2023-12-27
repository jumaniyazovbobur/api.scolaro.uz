package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.university.*;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.mapper.AppApplicationFilterMapperDTO;
import api.scolaro.uz.repository.appApplication.AppApplicationFilterRepository;
import api.scolaro.uz.repository.university.UniversityCustomRepository;
import api.scolaro.uz.repository.university.UniversityFacultyRepository;
import api.scolaro.uz.repository.university.UniversityRepository;
import api.scolaro.uz.service.consulting.ConsultingService;
import api.scolaro.uz.service.place.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class UniversityService {
    private final UniversityRepository universityRepository;
    @Autowired
    private UniversityCustomRepository customRepository;
    @Autowired
    private AttachService attachService;
    private final UniversityDegreeService universityDegreeService;
    private final CountryService countryService;
    private final UniversityFacultyService universityFacultyService;
    @Autowired
    private ConsultingService consultingService;
    @Autowired
    private UniversityFacultyRepository universityFacultyRepository;

    public ApiResponse<UniversityResponseDTO> create(UniversityCreateDTO dto) {
        UniversityEntity entity = new UniversityEntity();
        entity.setName(dto.getName());
        entity.setRating(dto.getRating());
        entity.setWebSite(dto.getWebSite());
        entity.setCountryId(dto.getCountryId());
        entity.setPhotoId(dto.getPhotoId());
        entity.setDescription(dto.getDescription());
        entity.setCreatedId(EntityDetails.getCurrentUserId());
        entity.setLogoId(dto.getLogoId());
        universityRepository.save(entity);
        universityDegreeService.merger(entity.getId(), dto.getDegreeList()); //merge university degreeType
        universityFacultyService.merger(entity.getId(), dto.getFacultyIdList());
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
        entity.setPhotoId(dto.getPhotoId());
        entity.setLogoId(dto.getLogoId());
        universityRepository.save(entity);
        universityDegreeService.merger(entity.getId(), dto.getDegreeList()); //merge university degreeType\
        universityFacultyService.merger(entity.getId(), dto.getFacultyIdList());
        return ApiResponse.ok(toDTO(entity));
    }

    public ApiResponse<UniversityResponseDTO> getById(Long id, AppLanguage appLanguage) {
        UniversityEntity entity = get(id);
        UniversityResponseDTO responseDTO = toDTO(entity);
        responseDTO.setDegreeTypeList(universityDegreeService.getUniversityDegreeTypeList(id)); // get degree list
        responseDTO.setFacultyIdList(universityFacultyRepository.getFacultyIdListByUniversityId(id)); // ser faculty id list
        return ApiResponse.ok(responseDTO);
    }

    public ApiResponse<UniversityResponseDTO> getByIdDetail(Long id, AppLanguage appLanguage) {
        UniversityEntity entity = get(id);
        UniversityResponseDTO responseDTO = toDTO(entity);
        responseDTO.setCountry(countryService.getById(entity.getCountryId(), appLanguage));
        responseDTO.setDegreeList(universityDegreeService.getUniversityDegreeTypeList(id, appLanguage));
        responseDTO.setFacultyList(universityFacultyService.getUniversityFacultyList(id, appLanguage));
        responseDTO.setConsultingList(consultingService.getUniversityConsultingList(id));  // university consulting list
        return ApiResponse.ok(responseDTO);
    }

    public UniversityResponseDTO getByIdDetailResponse(Long id, AppLanguage appLanguage) {
        UniversityEntity entity = get(id);
        UniversityResponseDTO responseDTO = toDTO(entity);
        responseDTO.setDegreeList(universityDegreeService.getUniversityDegreeTypeList(id, appLanguage));
        return responseDTO;
    }

    public PageImpl<UniversityResponseFilterDTO> filter(int page, int size, UniversityFilterDTO dto, AppLanguage language) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<UniversityEntity> universityList = customRepository.filterPagination(dto, page, size);
        List<UniversityResponseFilterDTO> dtoList = new LinkedList<>();
        for (UniversityEntity entity : universityList.getContent()) {
            UniversityResponseFilterDTO dto1 = toDTOForTop(entity, language);
            dtoList.add(dto1);
        }
        return new PageImpl<>(dtoList, pageable, universityList.getTotalElement());
    }

    public UniversityResponseDTO toDTO(UniversityEntity entity) {
        UniversityResponseDTO dto = new UniversityResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRating(entity.getRating());
        dto.setCountryId(entity.getCountryId());
        dto.setWebSite(entity.getWebSite());
        if (entity.getPhotoId() != null) {
            dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        }
        if (entity.getLogoId() != null) {
            dto.setLogo(attachService.getResponseAttach(entity.getLogoId()));
        }
        dto.setDescription(entity.getDescription());
        return dto;
    }

    private UniversityResponseFilterDTO toDTOForTop(UniversityEntity entity, AppLanguage language) {
        UniversityResponseFilterDTO dto = new UniversityResponseFilterDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRating(entity.getRating());
        if (entity.getCountryId() != null) {
            dto.setCountry(countryService.getById(entity.getCountryId(), language));
        }
        dto.setWebSite(entity.getWebSite());
        if (entity.getPhotoId() != null) {
            dto.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        }
        dto.setDegreeList(universityDegreeService.getUniversityDegreeTypeList(entity.getId(), language));
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
        dto.setLogo(attachService.getResponseAttach(entity.getLogoId()));
        return dto;
    }

    public ApiResponse<List<UniversityResponseFilterDTO>> getTopUniversity(AppLanguage language) {
        List<UniversityEntity> list = universityRepository.getTopUniversity();
        List<UniversityResponseFilterDTO> dtoList = new LinkedList<>();
        for (UniversityEntity entity : list) {
            UniversityResponseFilterDTO dto = toDTOForTop(entity, language);
            dtoList.add(dto);
        }
        return new ApiResponse<>(200, false, dtoList);
    }

    public List<UniversityResponseDTO> getConsultingUniversityList(String consultingId) {
        List<UniversityEntity> entityList = universityRepository.getUniversityListByConsultingId(consultingId);
        List<UniversityResponseDTO> dtoList = new LinkedList<>();
        for (UniversityEntity entity : entityList) {
            dtoList.add(toDTO(entity));
        }
        return dtoList;
    }

    // get university list for consulting. Consulting mobile first page
    public ApiResponse<Page<UniversityResponseDTO>> getApplicationUniversityListForConsulting_mobile(int page, int size) {
        String consultingId = EntityDetails.getCurrentUserDetail().getProfileConsultingId();
        String filterByConsultingProfileId = null;
        if (!EntityDetails.hasRoleCurrentUser(RoleEnum.ROLE_CONSULTING_MANAGER)) {
            filterByConsultingProfileId = EntityDetails.getCurrentUserId();
        }
        FilterResultDTO<UniversityResponseDTO> filterResult = customRepository.getApplicationUniversityListForConsulting_mobile(consultingId, filterByConsultingProfileId, page, size);
        Page<UniversityResponseDTO> pageObj = new PageImpl<>(filterResult.getContent(), PageRequest.of(page, size), filterResult.getTotalElement());
        return ApiResponse.ok(pageObj);
    }
}
