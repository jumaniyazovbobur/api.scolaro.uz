package api.scolaro.uz.service;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.FilterResultDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.university.*;
import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.GeneralStatus;
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
    @Autowired
    private FacultyService facultyService;

    public ApiResponse<UniversityResponseDTO> create(UniversityCreateDTO dto, AppLanguage appLanguage) {
        UniversityEntity entity = new UniversityEntity();
        entity.setName(dto.getName());
        entity.setRating(dto.getRating());
        entity.setWebSite(dto.getWebSite());
        entity.setCountryId(dto.getCountryId());
        entity.setPhotoId(dto.getPhotoId());
        entity.setCompressedPhotoId(attachService.getImageCompressedImageId(dto.getPhotoId()));
//        entity.setDescription(dto.getDescription());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setDescriptionRu(dto.getDescriptionRu());
//        entity.setAbbreviation(dto.getAbbreviation());
        entity.setAbbreviationUz(dto.getAbbreviationUz());
        entity.setAbbreviationEn(dto.getAbbreviationEn());
        entity.setAbbreviationRu(dto.getAbbreviationRu());
        entity.setCreatedId(EntityDetails.getCurrentUserId());
        entity.setLogoId(dto.getLogoId());
        entity.setCompressedLogoId(attachService.getImageCompressedImageId(dto.getLogoId()));
        universityRepository.save(entity);
        universityDegreeService.merger(entity.getId(), dto.getDegreeList()); //merge university degreeType
        universityFacultyService.collectAndMerge(entity.getId(), dto.getFacultyIdList());
        return ApiResponse.ok(toDTO(entity, appLanguage));
    }

    public ApiResponse<?> deleted(Long id) {
        UniversityEntity entity = get(id);
        int result = universityRepository.deleted(entity.getId(), EntityDetails.getCurrentUserId(), LocalDateTime.now());
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }

    public ApiResponse<UniversityResponseDTO> update(Long id, UniversityUpdateDTO dto, AppLanguage appLanguage) {
        UniversityEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setWebSite(dto.getWebSite());
        entity.setRating(dto.getRating());
        entity.setCountryId(dto.getCountryId());
//        entity.setDescription(dto.getDescription());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setDescriptionRu(dto.getDescriptionRu());
//        entity.setAbbreviation(dto.getAbbreviation());
        entity.setAbbreviationUz(dto.getAbbreviationUz());
        entity.setAbbreviationEn(dto.getAbbreviationEn());
        entity.setAbbreviationRu(dto.getAbbreviationRu());
        entity.setPhotoId(dto.getPhotoId()); // Delete old photo.
        entity.setCompressedPhotoId(attachService.getImageCompressedImageId(dto.getPhotoId()));
        entity.setLogoId(dto.getLogoId());
        entity.setCompressedLogoId(attachService.getImageCompressedImageId(dto.getLogoId()));
        universityRepository.save(entity);
        universityDegreeService.merger(entity.getId(), dto.getDegreeList()); //merge university degreeType\
        universityFacultyService.collectAndMerge(entity.getId(), dto.getFacultyIdList());
        return ApiResponse.ok(toDTO(entity, appLanguage));
    }

    public ApiResponse<UniversityResponseDTO> getById(Long id, AppLanguage appLanguage) {
        UniversityEntity entity = get(id);
        UniversityResponseDTO responseDTO = new UniversityResponseDTO();
        responseDTO.setCountry(countryService.getById(entity.getCountryId(), appLanguage));
        responseDTO.setDegreeList(universityDegreeService.getUniversityDegreeTypeList(id, appLanguage));
        responseDTO.setFacultyIdList(universityFacultyRepository.getFacultyIdListByUniversityId(id)); // set faculty id list
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setRating(entity.getRating());
        responseDTO.setWebSite(entity.getWebSite());
        if (entity.getPhotoId() != null) {
            responseDTO.setPhoto(attachService.getResponseAttach(entity.getPhotoId()));
        }
        if (entity.getLogoId() != null) {
            responseDTO.setLogo(attachService.getResponseAttach(entity.getLogoId()));
        }
        responseDTO.setDescriptionEn(entity.getDescriptionEn());
        responseDTO.setAbbreviationEn(entity.getAbbreviationEn());
        responseDTO.setDescriptionUz(entity.getDescriptionUz());
        responseDTO.setAbbreviationUz(entity.getAbbreviationUz());
        responseDTO.setDescriptionRu(entity.getDescriptionRu());
        responseDTO.setAbbreviationRu(entity.getAbbreviationRu());

        return ApiResponse.ok(responseDTO);
    }

    public ApiResponse<UniversityResponseDTO> getByIdDetail(Long id, AppLanguage appLanguage) {
        UniversityEntity entity = get(id);
        UniversityResponseDTO responseDTO = toDTO(entity, appLanguage);
        responseDTO.setCountry(countryService.getById(entity.getCountryId(), appLanguage));
        responseDTO.setDegreeList(universityDegreeService.getUniversityDegreeTypeList(id, appLanguage));
        responseDTO.setFacultyList(universityFacultyService.getUniversityFacultyList(id, appLanguage));
        responseDTO.setConsultingList(consultingService.getUniversityConsultingList(id));  // university consulting list
        return ApiResponse.ok(responseDTO);
    }

    public UniversityResponseDTO getByIdDetailResponse(Long id, AppLanguage appLanguage) {
        UniversityEntity entity = get(id);
//        UniversityResponseDTO responseDTO = toDTO(entity);
        UniversityResponseDTO dto = new UniversityResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRating(entity.getRating());
        dto.setCountryId(entity.getCountryId());
        dto.setWebSite(entity.getWebSite());
        //dto.setDegreeList(universityDegreeService.getUniversityDegreeTypeList(id, appLanguage));
        return dto;
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

    public UniversityResponseDTO toDTO(UniversityEntity entity, AppLanguage language) {
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
//        dto.setDescription(entity.getDescription());
//        dto.setAbbreviation(entity.getAbbreviation());
        switch (language) {
            case en -> {
                dto.setDescription(entity.getDescriptionEn());
                dto.setAbbreviation(entity.getAbbreviationEn());
            }
            case uz -> {
                dto.setDescription(entity.getDescriptionUz());
                dto.setAbbreviation(entity.getAbbreviationUz());
            }
            case ru -> {
                dto.setDescription(entity.getDescriptionRu());
                dto.setAbbreviation(entity.getAbbreviationRu());
            }
        }
        return dto;
    }

    private UniversityResponseFilterDTO toDTOForTop(UniversityEntity entity, AppLanguage language) {
        UniversityResponseFilterDTO dto = new UniversityResponseFilterDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRating(entity.getRating());
        dto.setStatus(entity.getStatus());
        if (entity.getCountryId() != null) {
            dto.setCountry(countryService.getById(entity.getCountryId(), language));
        }
        dto.setWebSite(entity.getWebSite());
        if (entity.getCompressedPhotoId() != null) {
            dto.setPhoto(attachService.getResponseAttach(entity.getCompressedPhotoId()));
        }
        dto.setDegreeList(universityDegreeService.getUniversityDegreeTypeList(entity.getId(), language));
//        dto.setDescription(entity.getDescription());
//        dto.setAbbreviation(entity.getAbbreviation());

        switch (language) {
            case en -> {
                dto.setDescription(entity.getDescriptionEn());
                dto.setAbbreviation(entity.getAbbreviationEn());
            }
            case uz -> {
                dto.setDescription(entity.getDescriptionUz());
                dto.setAbbreviation(entity.getAbbreviationUz());
            }
            case ru -> {
                dto.setDescription(entity.getDescriptionRu());
                dto.setAbbreviation(entity.getAbbreviationRu());
            }
        }
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
        List<UniversityEntity> list = universityRepository.getTopUniversity(PageRequest.of(0, 10));
        List<UniversityResponseFilterDTO> dtoList = new LinkedList<>();
        for (UniversityEntity entity : list) {
            UniversityResponseFilterDTO dto = toDTOForTop(entity, language);
            dtoList.add(dto);
        }
        return new ApiResponse<>(200, false, dtoList);
    }

    public List<UniversityResponseDTO> getConsultingUniversityList(String consultingId, AppLanguage appLanguage) {
        List<UniversityEntity> entityList = universityRepository.getUniversityListByConsultingId(consultingId);
        List<UniversityResponseDTO> dtoList = new LinkedList<>();
        for (UniversityEntity entity : entityList) {
            UniversityResponseDTO dto = new UniversityResponseDTO();
            if (entity.getCompressedPhotoId() != null) {
                dto.setPhoto(attachService.getResponseAttach(entity.getCompressedPhotoId()));
            }
            if (entity.getCompressedLogoId() != null) {
                dto.setLogo(attachService.getResponseAttach(entity.getCompressedLogoId()));
            }
            dtoList.add(toDTO(entity, appLanguage));
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

    public ApiResponse<String> changeStatus(Long id, GeneralStatus status) {
        int result = universityRepository.changeStatus(id, status);
        if (result == 0) return ApiResponse.bad("Try again !");
        return ApiResponse.ok("Success");
    }
}
