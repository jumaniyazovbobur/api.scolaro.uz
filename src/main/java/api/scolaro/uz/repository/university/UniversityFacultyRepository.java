package api.scolaro.uz.repository.university;

import api.scolaro.uz.entity.UniversityFacultyEntity;
import api.scolaro.uz.mapper.FacultyMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UniversityFacultyRepository extends CrudRepository<UniversityFacultyEntity, String> {
    @Query("select u.facultyId from UniversityFacultyEntity as u where u.universityId =:universityId")
    List<String> getFacultyIdListByUniversityId(@Param("universityId") Long universityId);

    @Transactional
    @Modifying
    void deleteByUniversityIdAndFacultyId(Long universityId, String facultyId);

    @Query(value = "select f.id, CASE :lang WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END as name, order_number as orderNumber " +
            "from university_faculty as u " +
            "inner join faculty as f on f.id = u.faculty_id " +
            "where u.university_id =:universityId", nativeQuery = true)
    List<FacultyMapper> getUniversityFacultyList(@Param("universityId") Long universityId, @Param("lang") String lang);
}
