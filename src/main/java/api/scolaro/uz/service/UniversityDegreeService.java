package api.scolaro.uz.service;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.entity.UniversityDegreeTypeEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.UniversityDegreeType;
import api.scolaro.uz.repository.university.UniversityDegreeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UniversityDegreeService {
    public static final List<KeyValueDTO> uzList = new LinkedList<>();
    public static final List<KeyValueDTO> enList = new LinkedList<>();
    public static final List<KeyValueDTO> ruList = new LinkedList<>();

    static {
        for (UniversityDegreeType type : UniversityDegreeType.values()) {
            uzList.add(new KeyValueDTO(type.name(), type.getNameUz()));
            enList.add(new KeyValueDTO(type.name(), type.getNameEn()));
            ruList.add(new KeyValueDTO(type.name(), type.toString()));
        }
    }

    @Autowired
    private UniversityDegreeTypeRepository universityDegreeTypeRepository;

    public ApiResponse<List<KeyValueDTO>> getList(AppLanguage appLanguage) {
        switch (appLanguage) {
            case uz -> {
                return ApiResponse.ok(uzList);
            }
            case en -> {
                return ApiResponse.ok(enList);
            }
            default -> {
                return ApiResponse.ok(ruList);
            }
        }
    }

    public void merger(Long universityId, List<UniversityDegreeType> newList) {
        List<UniversityDegreeType> oldList = universityDegreeTypeRepository.getUniversityDegreeTypeListByUniversityId(universityId);

        if (newList != null) {
            newList.forEach(degreeType -> {
                if (!oldList.contains(degreeType)) {
                    createCourseTopic(universityId, degreeType);
                }
            });
            oldList.forEach(oldDegreeType -> {
                long count = newList.stream()
                        .filter(newDegreeType -> newDegreeType.equals(oldDegreeType)).count();
                if (count == 0) {
                    universityDegreeTypeRepository.deleteByUniversityIdAndDegreeType(universityId, oldDegreeType);
                }
            });
        }
    }

    public void createCourseTopic(Long university, UniversityDegreeType degreeType) {
        UniversityDegreeTypeEntity entity = new UniversityDegreeTypeEntity();
        entity.setUniversityId(university);
        entity.setDegreeType(degreeType);
        universityDegreeTypeRepository.save(entity);
    }

    public List<UniversityDegreeType> getUniversityDegreeTypeList(Long universityId) {
        return universityDegreeTypeRepository.getUniversityDegreeTypeList(universityId);
    }

    public List<KeyValueDTO> getUniversityDegreeTypeList(Long universityId, AppLanguage appLanguage) {
        List<UniversityDegreeType> topicEntityList = universityDegreeTypeRepository.getUniversityDegreeTypeList(universityId);
        List<KeyValueDTO> dtoList = new LinkedList<>();
        for (UniversityDegreeType degreeType : topicEntityList) {
            KeyValueDTO dto = new KeyValueDTO();
            dto.setKey(degreeType.name());
            switch (appLanguage) {
                case uz -> {
                    dto.setValue(degreeType.getNameUz());
                }
                case en -> {
                    dto.setValue(degreeType.getNameEn());
                }
                default -> {
                    dto.setValue(degreeType.getNameRu());
                }
            }
            dtoList.add(dto);
        }
        return dtoList;
    }
}
