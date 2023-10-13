package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.entity.consulting.ConsultingTariffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultingTariffRepository extends JpaRepository<ConsultingTariffEntity, String> {

    Optional<ConsultingTariffEntity> findByIdAndVisibleTrue(String id);

    @Query("select ct from ConsultingTariffEntity as ct where ct.consultingId=:consultingId order by ct.order")
    List<ConsultingTariffEntity> getByConsultingId(@Param("consultingId") String id);

    @Query("From ConsultingTariffEntity as ct where ct.tariffType='TEMPLATE' order by ct.order")
    List<ConsultingTariffEntity> getConsultingTariffEntitiesByTariffType();

    @Modifying
    @Transactional
    @Query("update ConsultingTariffEntity set visible = false ,deletedDate = ?2, deletedId = ?3  where id = ?1")
    int updateVisibleIsFalse(String id, LocalDateTime now, String deletedId);

}
