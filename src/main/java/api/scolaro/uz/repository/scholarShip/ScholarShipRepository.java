package api.scolaro.uz.repository.scholarShip;

import api.scolaro.uz.entity.scholarShip.ScholarShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScholarShipRepository extends JpaRepository<ScholarShipEntity, String> {
    Optional<ScholarShipEntity> findByNameAndVisibleIsTrue(String name);

    Optional<ScholarShipEntity> findByIdAndVisibleTrue(String name);
    List<ScholarShipEntity> findAllByVisibleTrue();


    @Modifying
    @Transactional
    @Query("update ScholarShipEntity t set t.deletedDate = ?2, t.visible = false where t.id = ?1")
    int updateDeletedDateAndVisible(String id, LocalDateTime deletedDate);

    @Query("from ScholarShipEntity s left join fetch s.university left join fetch s.photo where s.visible = true and s.expiredDate >=now()")
    List<ScholarShipEntity> getTopScholarShip();
}
