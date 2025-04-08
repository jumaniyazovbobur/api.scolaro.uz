package api.scolaro.uz.repository;

import api.scolaro.uz.dto.webStudent.WebStudentResponseDTO;
import api.scolaro.uz.entity.WebStudentEntity;
import api.scolaro.uz.mapper.WebStudentMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WebStudentRepository extends JpaRepository<WebStudentEntity,String> {

    Optional<WebStudentEntity> findByIdAndVisibleTrue(String id);

    List<WebStudentEntity> findByVisibleTrueOrderByOrderNumber();

    @Query(value = """
    SELECT
        id,
        full_name As FullName,
        order_number As OrderNumber,
        photo_id As PhotoId,
        CASE
            WHEN :lang = 'uz' THEN about_uz
            WHEN :lang = 'ru' THEN about_ru
            WHEN :lang = 'en' THEN about_en
            ELSE about_uz
        END AS about
    FROM web_student WHERE visible = true
    ORDER BY order_number
    """, nativeQuery = true)
    List<WebStudentMapper> findAllByLang(@Param("lang") String lang);
}
