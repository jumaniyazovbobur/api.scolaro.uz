package api.scolaro.uz.repository;

import api.scolaro.uz.entity.ContinentCountryEntity;
import api.scolaro.uz.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ContinentCountryRepository extends JpaRepository<ContinentCountryEntity, Long> {
    @Query("select new CountryEntity(d.id,d.nameUz,d.nameRu,d.nameEn) from ContinentCountryEntity as c inner join c.country as d where c.continentId=:continentId " + "and d.visible =true order by d.createdDate")
    Iterable<CountryEntity> findByContinentId(@Param("continentId") Long continentId);

    @Modifying
    @Transactional
    @Query("Delete FROM ContinentCountryEntity where continentId =:continentId  and countryId=:countryId")
    void deleted(@Param("continentId") Long continentId, @Param("countryId") Long countryId);


    Optional<ContinentCountryEntity> findByContinentIdAndCountryId(Long conId, Long countId);
}
