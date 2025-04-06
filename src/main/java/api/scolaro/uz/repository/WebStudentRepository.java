package api.scolaro.uz.repository;

import api.scolaro.uz.dto.webStudent.WebStudentResponseDTO;
import api.scolaro.uz.entity.WebStudentEntity;
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
        full_name,
        order_number,
        photo_id,
        CASE
            WHEN :lang = 'uz' THEN about_uz
            WHEN :lang = 'ru' THEN about_ru
            WHEN :lang = 'en' THEN about_en
            ELSE about_uz
        END AS about
    FROM web_student WHERE visible = true
    ORDER BY order_number
    """, nativeQuery = true)
    List<Map<String, Object>> findAllByLang(@Param("lang") String lang);

//    @Query("""
//    SELECT new api.scolaro.uz.dto.webStudent.WebStudentResponseDTO(
//        w.id,
//        w.fullName,
//        CASE
//            WHEN :lang = 'uz' THEN w.aboutUz
//            WHEN :lang = 'ru' THEN w.aboutRu
//            WHEN :lang = 'en' THEN w.aboutEn
//            ELSE w.aboutUz
//        END,
//        null,  -- aboutUz
//        null,  -- aboutRu
//        null,  -- aboutEn
//        w.photoId,
//        w.orderNumber
//    )
//    FROM WebStudentEntity AS w
//    ORDER BY w.orderNumber
//""")
//    List<WebStudentResponseDTO> findAllByLang(@Param("lang") String lang);




}
