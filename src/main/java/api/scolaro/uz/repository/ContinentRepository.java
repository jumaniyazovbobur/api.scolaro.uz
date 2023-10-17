package api.scolaro.uz.repository;

import api.scolaro.uz.entity.place.ContinentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface ContinentRepository extends JpaRepository<ContinentEntity,Long> {
    Iterable<ContinentEntity> findAllByVisibleTrueOrderByOrderNumber();

    @Modifying
    @Transactional
    @Query("Update ContinentEntity set visible = false , deletedId=:deletedId, deletedDate=:deletedDate where id =:id")
    int deleted(@Param("id") Long id,
                @Param("deletedId") String currentUserId,
                @Param("deletedDate") LocalDateTime now);
}
