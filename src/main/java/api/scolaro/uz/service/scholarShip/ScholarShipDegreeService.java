package api.scolaro.uz.service.scholarShip;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.entity.UniversityDegreeTypeEntity;
import api.scolaro.uz.entity.scholarShip.ScholarShipDegreeTypeEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.UniversityDegreeType;
import api.scolaro.uz.repository.scholarShip.ScholarShipDegreeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ScholarShipDegreeService {
    public static final List<KeyValueDTO> uzList = new LinkedList<>();
    public static final List<KeyValueDTO> enList = new LinkedList<>();
    public static final List<KeyValueDTO> ruList = new LinkedList<>();

    static {
        for (UniversityDegreeType type : UniversityDegreeType.values()) {
            uzList.add(new KeyValueDTO(type.name(), type.getNameUz()));
            enList.add(new KeyValueDTO(type.name(), type.getNameEn()));
            ruList.add(new KeyValueDTO(type.name(), type.getNameRu()));
        }
    }

    @Autowired
    private ScholarShipDegreeTypeRepository scholarShipDegreeTypeRepository;

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

    public void merger(String scholarShipId, List<UniversityDegreeType> newList) {
        List<UniversityDegreeType> oldList = scholarShipDegreeTypeRepository.getScholarShipDegreeTypeListByScholarShipId(scholarShipId);

        if (newList != null) {
            newList.forEach(degreeType -> {
                if (!oldList.contains(degreeType)) {
                    createCourseTopic(scholarShipId, degreeType);
                }
            });
            oldList.forEach(oldDegreeType -> {
                long count = newList.stream()
                        .filter(newDegreeType -> newDegreeType.equals(oldDegreeType)).count();
                if (count == 0) {
                    scholarShipDegreeTypeRepository.deleteByScholarShipIdAndDegreeType(scholarShipId, oldDegreeType);
                }
            });
        }
    }

    public void createCourseTopic(String scholarShipId, UniversityDegreeType degreeType) {
        ScholarShipDegreeTypeEntity entity = new ScholarShipDegreeTypeEntity();
        entity.setScholarShipId(scholarShipId);
        entity.setDegreeType(degreeType);
        scholarShipDegreeTypeRepository.save(entity);
    }

    public List<UniversityDegreeType> getScholarShipDegreeTypeList(String universityId) {
        return scholarShipDegreeTypeRepository.getScholarShipDegreeTypeList(universityId);
    }

    public List<KeyValueDTO> getScholarShipDegreeTypeList(String scholarShipId, AppLanguage appLanguage) {
        List<UniversityDegreeType> topicEntityList = scholarShipDegreeTypeRepository.getScholarShipDegreeTypeList(scholarShipId);
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
