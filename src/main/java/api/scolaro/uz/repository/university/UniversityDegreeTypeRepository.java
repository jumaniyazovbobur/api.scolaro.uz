package api.scolaro.uz.repository.university;

import api.scolaro.uz.entity.UniversityDegreeTypeEntity;
import api.scolaro.uz.enums.UniversityDegreeType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UniversityDegreeTypeRepository extends CrudRepository<UniversityDegreeTypeEntity, Long> {
    @Query("select u.degreeType from UniversityDegreeTypeEntity as u where u.universityId =:universityId")
    List<UniversityDegreeType> getUniversityDegreeTypeListByUniversityId(@Param("universityId") Long universityId);

    @Transactional
    @Modifying
    void deleteByUniversityIdAndDegreeType(Long universityId, UniversityDegreeType degreeType);

    @Query("select t.degreeType from UniversityDegreeTypeEntity as t where t.universityId =:universityId")
    List<UniversityDegreeType> getUniversityDegreeTypeList(@Param("universityId") Long universityId);
}
