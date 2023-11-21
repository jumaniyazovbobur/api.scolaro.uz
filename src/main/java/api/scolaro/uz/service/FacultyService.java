package api.scolaro.uz.service;

import api.scolaro.uz.dto.*;
import api.scolaro.uz.dto.profile.ProfileFilterDTO;
import api.scolaro.uz.dto.profile.ProfileResponseDTO;
import api.scolaro.uz.entity.FacultyEntity;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.place.RegionEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;

    public ApiResponse<List<FacultyDTO>> filter(FacultyFilterDTO filterDTO, int page, int size) {
        List<FacultyEntity> all = facultyRepository.getAllByVisibleIsTrueOrderByCreatedDateDesc();
        List<FacultyDTO> dtoList = new ArrayList<>();
        for (FacultyEntity entity : all) {
            FacultyDTO dto = toDetailDTO(entity);
            dtoList.add(dto);
        }
        return ApiResponse.ok(dtoList);
    }

    public ApiResponse<List<FacultyDTO>> filterPublic(AppLanguage appLanguage) {
        List<FacultyEntity> all = facultyRepository.getAllByVisibleIsTrueOrderByCreatedDateDesc();
        List<FacultyDTO> dtoList = new ArrayList<>();
        for (FacultyEntity entity : all) {
            FacultyDTO dto = toDTO(entity, appLanguage);
            dtoList.add(dto);
        }
        return ApiResponse.ok(dtoList);
    }


    public FacultyDTO create(FacultyCreateDTO dto) {
        FacultyEntity entity = new FacultyEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setVisible(Boolean.TRUE);
        entity.setCreatedDate(LocalDateTime.now());
        facultyRepository.save(entity);
        return toDetailDTO(entity);
    }

    public FacultyDTO update(String id, FacultyCreateDTO dto) {
        FacultyEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        facultyRepository.save(entity);
        return toDetailDTO(entity);
    }

    @Transactional
    public Boolean deleteById(String id) {
        FacultyEntity entity = get(id);
        entity.setVisible(Boolean.FALSE);
        facultyRepository.save(entity);
        return true;
    }

    public FacultyDTO findById(String id) {
        FacultyEntity entity = get(id);
        return toDetailDTO(entity);
    }

    // filter, by lang filter

//    public ApiResponse<PageImpl<FacultyDTO>> filter(FacultyFilterDTO dto, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Pageable  all = facultyRepository.getAllByVisibleIsTrueOrderByCreatedDateDesc(pageable);
//        List<FacultyDTO> dtoList = new ArrayList<>();
//        for (FacultyEntity entity ) {
//            FacultyDTO dto = toDetailDTO(entity);
//            dtoList.add(dto);
//        }
//        return dtoList;
//        FilterResultDTO<ProfileEntity> filterResultDTO = customProfileRepository.filterPagination(dto, page, size);
//        return ApiResponse.ok(new PageImpl<>(filterResultDTO
//                .getContent()
//                .stream()
//                .map(this::getResponseDto)
//                .toList(), pageable, filterResultDTO.getTotalElement()));
//    }

/*
    public PageImpl<UniversityResponseFilterDTO> filter(int page, int size, UniversityFilterDTO dto) {
        Pageable pageable = PageRequest.of(page, size);
        FilterResultDTO<UniversityEntity> universityList = customRepository.filterPagination(dto, page, size);
        List<UniversityResponseFilterDTO> dtoList = new LinkedList<>();
        for (UniversityEntity entity : universityList.getContent()) {
            UniversityResponseFilterDTO dto1 = toDTOForTop(entity, language);
            dtoList.add(dto1);
        }
        return new PageImpl<>(dtoList, pageable, universityList.getTotalElement());
    }*/

    public FacultyDTO toDTO(FacultyEntity entity, AppLanguage appLanguage) {
        FacultyDTO dto = new FacultyDTO();
        dto.setId(entity.getId());
        switch (appLanguage) {
            case en:
                dto.setName(entity.getNameUz());
            case ru:
                dto.setName(entity.getNameEn());
            default:
                dto.setName(entity.getNameRu());
        }
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public FacultyDTO toDetailDTO(FacultyEntity entity) {
        FacultyDTO dto = new FacultyDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public FacultyEntity get(String id) {
        return facultyRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("faculty not found");
        });
    }

}
