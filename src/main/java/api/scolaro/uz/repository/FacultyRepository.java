package api.scolaro.uz.repository;

import api.scolaro.uz.entity.FacultyEntity;
import api.scolaro.uz.mapper.FacultyTreeMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacultyRepository extends CrudRepository<FacultyEntity, String> {
    @Query(value = "select f.id, " +
            "       CASE :lang WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END as name, " +
            "       f.order_number as orderNumber, " +
            "       get_sub_faculty(f.id,'en') as subFaculty," +
            "       get_faculty_university_count(f.id) as universityCount " +
            "from faculty as f " +
            "where parent_id isnull and visible=true;", nativeQuery = true)
    List<FacultyTreeMapper> getFacultyTree(@Param("lang") String lang);

    @Query(value = "select f.id, " +
            "       CASE :lang WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END as name, " +
            "       f.order_number as orderNumber, " +
            "       get_university_sub_faculty(f.id,'en',:universityId) as subFaculty," +
            "       (select exists(select true from university_faculty where university_id =:universityId and faculty_id =f.id and visible = true )) as isSelected " +
            "from faculty as f " +
            "where parent_id isnull and visible=true;", nativeQuery = true)
    List<FacultyTreeMapper> getFacultyTreeForUniversity(@Param("universityId") Long universityId, @Param("lang") String lang);

    @Query(value = "select f.id,\n" +
            "       CASE :lang WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END as name,\n" +
            "       f.order_number as orderNumber,\n" +
            "       get_faculty_university_count(f.id) as universityCount\n" +
            "from faculty as f\n" +
            "where parent_id isnull and visible = true;", nativeQuery = true)
    List<FacultyTreeMapper> getFirstLevelFacultyListWithUniversityCount(@Param("lang") String lang);

    @Query(value = "select f.id, " +
            "       CASE :lang WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END as name, " +
            "       f.order_number as orderNumber, " +
            "       get_sub_faculty(f.id,'en') as subFaculty," +
            "       get_faculty_university_count(f.id) as universityCount " +
            "from faculty as f " +
            "where parent_id =:parentId and visible=true", nativeQuery = true)
    List<FacultyTreeMapper> getFacultySubTreeWithUniversityCount(@Param("parentId") String parentId, @Param("lang") String lang);


    @Query(value = "select f.id, " +
            "       CASE :lang WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END as name, " +
            "       f.order_number as orderNumber " +
            "from faculty as f " +
            "where parent_id isnull visible=true ", nativeQuery = true)
    List<FacultyTreeMapper> getFirstLevelFacultyList(@Param("lang") String lang);

    @Query(value = "select f.id, " +
            "       CASE :lang WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END as name, " +
            "       f.order_number as orderNumber " +
            "from faculty as f " +
            "where parent_id =:parentId visible=true ", nativeQuery = true)
    List<FacultyTreeMapper> getFacultySubList(@Param("parentId") String parentId, @Param("lang") String lang);

    @Query(value = "select get_faculty_parent_list(:facultyId)", nativeQuery = true)
    String finFacultyParentIdList(@Param("facultyId") String facultyId);
}
