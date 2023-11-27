package api.scolaro.uz.repository;

import api.scolaro.uz.entity.place.ContinentEntity;
import api.scolaro.uz.mapper.ContinentMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ContinentRepository extends JpaRepository<ContinentEntity, Long> {
    Iterable<ContinentEntity> findAllByVisibleTrueOrderByOrderNumber();

    @Modifying
    @Transactional
    @Query("Update ContinentEntity set visible = false , deletedId=:deletedId, deletedDate=:deletedDate where id =:id")
    int deleted(@Param("id") Long id,
                @Param("deletedId") String currentUserId,
                @Param("deletedDate") LocalDateTime now);

    @Query(value = "select c.id, temp_t.universityCount, " +
            "       CASE :lang WHEN 'uz' THEN c.name_uz WHEN 'en' THEN c.name_en else c.name_ru END as name " +
            "from (select  continent.id, count(*) as universityCount " +
            "        from  continent " +
            "        inner join continent_country cc on continent.id = cc.continent_id " +
            "        inner join university u on cc.country_id = u.country_id " +
            "        group by continent.id) as temp_t " +
            "inner join continent as c on c.id = temp_t.id;", nativeQuery = true)
    List<ContinentMapper> getContinentListWithUniversityCount(@Param("lang") String lang);
}
