package api.scolaro.uz.repository.place;

import api.scolaro.uz.entity.place.CountryEntity;
import api.scolaro.uz.mapper.CountryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Iterable<CountryEntity> findAllByVisibleTrueOrderByNameUzAsc();

    Iterable<CountryEntity> findAllByVisibleTrueOrderByNameRuAsc();

    Iterable<CountryEntity> findAllByVisibleTrueOrderByNameEnAsc();

    Optional<CountryEntity> findByIdAndVisibleTrue(Long id);


    @Query(value = "select c.id, " +
            "    CASE :lang WHEN 'uz' THEN c.name_uz WHEN 'en' THEN c.name_en else c.name_ru END as name, " +
            "    (select count(*) from university u where u.country_id = c.id and u.visible=true) as universityCount " +
            "from country as c where c.visible=true; ", nativeQuery = true)
    List<CountryMapper> getCountryWithUniversityCount(@Param("lang") String lang);

    @Query(value = "select c.id, " +
            "    CASE :lang WHEN 'uz' THEN c.name_uz WHEN 'en' THEN c.name_en else c.name_ru END as name, " +
            "    (select count(*) from university u where u.country_id = c.id and u.visible=true) as universityCount " +
            "from country as c " +
            "inner join continent_country cc on c.id = cc.country_id " +
            "where c.visible=true and cc.continent_id =:continentId", nativeQuery = true)
    List<CountryMapper> getCountryListWithUniversityCountByContinentId(@Param("continentId") Long continentId, @Param("lang") String lang);

    @Modifying
    @Transactional
    @Query("Update CountryEntity set visible = :visible where id =:id")
    int deleteStatus(@Param("visible") boolean b, @Param("id") Long id);

    Page<CountryEntity> getAllByVisibleTrue(Pageable pageable);


    Optional<Object> getCountryByIdAndVisibleTrue(Long id);

    @Modifying
    @Transactional
    @Query("Update CountryEntity set visible = false , deletedId=:deletedId, deletedDate=:deletedDate where id =:id")
    int deleted(@Param("id") Long id,
                @Param("deletedId") String currentUserId,
                @Param("deletedDate") LocalDateTime now);

    @Query("FROM CountryEntity where  lower(nameUz) like :query or lower(nameEn) like :query or lower(nameRu) like :query order by createdDate")
    List<CountryEntity> searchByName(@Param("query") String query);
}
