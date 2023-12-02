package api.scolaro.uz.service;

import api.scolaro.uz.dto.*;
import api.scolaro.uz.dto.profile.ProfileFilterDTO;
import api.scolaro.uz.dto.profile.ProfileResponseDTO;
import api.scolaro.uz.entity.FacultyEntity;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.place.RegionEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.mapper.FacultyTreeMapper;
import api.scolaro.uz.repository.FacultyRepository;
import api.scolaro.uz.repository.consulting.ConsultingCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private ConsultingCustomRepository consultingCustomRepository;

    public FacultyDTO create(FacultyCreateDTO dto) {
        FacultyEntity entity = new FacultyEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setVisible(Boolean.TRUE);
        entity.setParentId(dto.getParentId());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setOrderNumber(dto.getOrderNumber());
        facultyRepository.save(entity);
        return toDetailDTO(entity);
    }

    public FacultyDTO update(String id, FacultyCreateDTO dto) {
        FacultyEntity entity = get(id);
        entity.setNameUz(dto.getNameUz());
        entity.setNameEn(dto.getNameEn());
        entity.setNameRu(dto.getNameRu());
        entity.setOrderNumber(dto.getOrderNumber());
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

    public ApiResponse<Page<FacultyDTO>> adminFilter(FacultyFilterDTO filterDTO, int page, int size) {
        return ApiResponse.ok(consultingCustomRepository.adminFilter(filterDTO, page, size));
    }

    public ApiResponse<Page<FacultyDTO>> publicFilter(FacultyFilterDTO filterDTO, AppLanguage appLanguage, int page, int size) {
        return ApiResponse.ok(consultingCustomRepository.publicFilter(filterDTO, appLanguage, page, size));
    }

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
        dto.setOrderNumber(entity.getOrderNumber());
        return dto;
    }

    public FacultyDTO toDetailDTO(FacultyEntity entity) {
        FacultyDTO dto = new FacultyDTO();
        dto.setId(entity.getId());
        dto.setNameUz(entity.getNameUz());
        dto.setNameEn(entity.getNameEn());
        dto.setNameRu(entity.getNameRu());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setOrderNumber(entity.getOrderNumber());
        return dto;
    }

    public FacultyEntity get(String id) {
        return facultyRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("faculty not found");
        });
    }

    public ApiResponse<List<FacultyDTO>> getFacultyTree(AppLanguage appLanguage) {
        List<FacultyTreeMapper> mapperList = facultyRepository.getFacultyTree(appLanguage.name());
        List<FacultyDTO> dtoList = new LinkedList<>();
        for (FacultyTreeMapper mapper : mapperList) {
            FacultyDTO dto = new FacultyDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setSubFaculty(mapper.getSubFaculty());
            dto.setUniversityCount(mapper.getUniversityCount());
            dtoList.add(dto);
        }
        return ApiResponse.ok(dtoList);
    }

    public ApiResponse<List<FacultyDTO>> getFirstLevelFacultyListWithUniversityCount(AppLanguage appLanguage) {
        List<FacultyTreeMapper> mapperList = facultyRepository.getFirstLevelFacultyListWithUniversityCount(appLanguage.name());
        List<FacultyDTO> dtoList = new LinkedList<>();
        for (FacultyTreeMapper mapper : mapperList) {
            FacultyDTO dto = new FacultyDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setUniversityCount(mapper.getUniversityCount());
            dtoList.add(dto);
        }
        return ApiResponse.ok(dtoList);
    }

    public ApiResponse<List<FacultyDTO>> getSubFacultyTreeWithUniversityCount(String parentFacultyId, AppLanguage appLanguage) {
        List<FacultyTreeMapper> mapperList = facultyRepository.getFacultySubTreeWithUniversityCount(parentFacultyId, appLanguage.name());
        List<FacultyDTO> dtoList = new LinkedList<>();
        for (FacultyTreeMapper mapper : mapperList) {
            FacultyDTO dto = new FacultyDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setOrderNumber(mapper.getOrderNumber());
            dto.setSubFaculty(mapper.getSubFaculty());
            dto.setUniversityCount(mapper.getUniversityCount());
            dtoList.add(dto);
        }
        return ApiResponse.ok(dtoList);
    }

    public ApiResponse<List<FacultyDTO>> facultyLevel(String parentFacultyId, AppLanguage appLanguage) {
        List<FacultyTreeMapper> mapperList;
        if (parentFacultyId == null) {
            mapperList = facultyRepository.getFirstLevelFacultyList(appLanguage.name());
        } else {
            mapperList = facultyRepository.getFacultySubList(parentFacultyId, appLanguage.name());
        }
        List<FacultyDTO> dtoList = new LinkedList<>();
        for (FacultyTreeMapper mapper : mapperList) {
            FacultyDTO dto = new FacultyDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setOrderNumber(mapper.getOrderNumber());
            dtoList.add(dto);
        }
        return ApiResponse.ok(dtoList);
    }

}
