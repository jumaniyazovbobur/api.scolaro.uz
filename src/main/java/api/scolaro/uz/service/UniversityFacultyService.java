package api.scolaro.uz.service;

import api.scolaro.uz.dto.FacultyDTO;
import api.scolaro.uz.entity.FacultyEntity;
import api.scolaro.uz.entity.UniversityDegreeTypeEntity;
import api.scolaro.uz.entity.UniversityFacultyEntity;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.UniversityDegreeType;
import api.scolaro.uz.repository.FacultyRepository;
import api.scolaro.uz.repository.university.UniversityFacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UniversityFacultyService {
    @Autowired
    private UniversityFacultyRepository universityFacultyRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    public void collectAndMerge(Long universityId, List<String> newList) {
        Map<String, String> facultyIdMap = new HashMap<>();
        for (String facultyId : newList) {
            facultyIdMap.put(facultyId, facultyId);
            String facultyIdStr = facultyRepository.finFacultyParentIdList(facultyId);
            if (!facultyIdStr.isBlank()) {
                String[] idList = facultyIdStr.split(",");
                for (String id : idList) {
                    if (!id.isBlank()) {
                        facultyIdMap.put(id, id);
                    }
                }
            }
        }
        merger(universityId, new LinkedList<>(facultyIdMap.values()));
    }

    public void merger(Long universityId, List<String> newList) {
        List<String> oldList = universityFacultyRepository.getFacultyIdListByUniversityId(universityId);
        if (newList != null) {
            newList.forEach(newItem -> {
                if (!oldList.contains(newItem)) {
                    createCourseTopic(universityId, newItem);
                }
            });
            oldList.forEach(oldItem -> {
                long count = newList.stream()
                        .filter(newDegreeType -> newDegreeType.equals(oldItem)).count();
                if (count == 0) {
                    universityFacultyRepository.deleteByUniversityIdAndFacultyId(universityId, oldItem);
                }
            });
        }
    }

    public void createCourseTopic(Long university, String facultyId) {
        UniversityFacultyEntity entity = new UniversityFacultyEntity();
        entity.setFacultyId(facultyId);
        entity.setUniversityId(university);
        universityFacultyRepository.save(entity);
    }

    public List<FacultyDTO> getUniversityFacultyList(Long universityId, AppLanguage appLanguage) {
        return universityFacultyRepository.getUniversityFacultyList(universityId, appLanguage.name()).stream().map(mapper -> {
            FacultyDTO dto = new FacultyDTO();
            dto.setId(mapper.getId());
            dto.setName(mapper.getName());
            dto.setOrderNumber(mapper.getOrderNumber());
            return dto;
        }).collect(Collectors.toList());
    }

}
