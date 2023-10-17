package api.scolaro.uz.repository.place;

import api.scolaro.uz.entity.place.CountryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    Iterable<CountryEntity> findAllByVisibleTrueOrderByNameUzAsc();

    Iterable<CountryEntity> findAllByVisibleTrueOrderByNameRuAsc();

    Iterable<CountryEntity> findAllByVisibleTrueOrderByNameEnAsc();

    Optional<CountryEntity> findByIdAndVisibleTrue(Long id);


//    @Query("SELECT  cou.id as cou_id,cou.visible as cou_visible,cou.createdDate as cou_createdDate," +
//            " cou.nameEn as nameEn,cou.nameUz as nameUz," +
//            " cou.nameRu as nameRu from CountryEntity as cou " +
//            " WHERE cou.id=:id and cou.visible = true ")
//    Optional<CountryMapper> getCountryByKey(@Param("id") Long id);


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
}
