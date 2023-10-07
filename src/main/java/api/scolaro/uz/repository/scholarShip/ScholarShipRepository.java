package api.scolaro.uz.repository.scholarShip;

import api.scolaro.uz.entity.ScholarShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScholarShipRepository extends JpaRepository<ScholarShipEntity, String> {
    Optional<ScholarShipEntity> findByNameAndVisibleIsTrue(String name);

    @Modifying
    @Transactional
    @Query("update ScholarShipEntity t set t.deletedDate = ?2, t.visible = false where t.id = ?1")
    boolean updateDeletedDateAndVisible(String id, LocalDateTime deletedDate);
}
