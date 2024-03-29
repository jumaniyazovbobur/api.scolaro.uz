package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.entity.consulting.ConsultingUniversityEntity;
import api.scolaro.uz.enums.UniversityDegreeType;
import api.scolaro.uz.mapper.ConsultingUniversityMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConsultingUniversityRepository extends CrudRepository<ConsultingUniversityEntity, String> {
    @Query("from ConsultingUniversityEntity where consultingId =:consultingId")
    List<ConsultingUniversityEntity> getConsultingUniversityList(@Param("consultingId") String consultingId);
    @Transactional

    @Modifying
    void deleteByConsultingIdAndUniversityId(String consultingId, Long universityId);

    /*@Transactional
    @Modifying
    @Query("update ConsultingUniversityEntity set tariffId =:tariffId where consultingId =:consultingId and universityId=:universityId")
    void updateTariff(@Param("consultingId") String consultingId, @Param("universityId") Long universityId, @Param("tariffId") String tariffId);
*/
    @Transactional
    @Modifying
    @Query("update ConsultingUniversityEntity set universityId=:universityId where consultingId =:consultingId ")
    void updateUniversity(@Param("consultingId") String consultingId, @Param("universityId") Long universityId);


    @Transactional
    @Modifying
    @Query(value = "select c.id,case when :lang = 'uz' then name_uz when :lang='en' then name_en else name_ru end as name,\n" +
            "       (select json_agg(temp_t1) " +
            "        from (select u.id,u.name, " +
            "                       (select id from consulting_university " +
            "                                    where consulting_id =:consultingId and university_id = u.id) as cuId" +
            "               from university u " +
            "              where u.visible =  true " +
            "                and u.country_id = c.id) " +
            "                 as temp_t1) as universityList " +
            "from country  as c " +
            "where  c.visible = true;", nativeQuery = true)
    List<ConsultingUniversityMapper> getUniversityListWithConsulting(@Param("consultingId") String consultingId, @Param("lang") String lang);
}
