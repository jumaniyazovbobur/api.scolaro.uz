package api.scolaro.uz.repository.consultinStep;

import api.scolaro.uz.entity.consulting.ConsultingStepEntity;
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
public interface ConsultingStepRepository extends JpaRepository<ConsultingStepEntity, String> {

    Optional<ConsultingStepEntity> findByIdAndVisibleTrue(String id);

    @Modifying
    @Transactional
    @Query("Update ConsultingStepEntity set visible = false , deletedId=:deletedId, deletedDate=:deletedDate where id =:id")
    int deleted(@Param("id") String id, @Param("deletedId") String currentUserId, @Param("deletedDate") LocalDateTime now);

    @Query(" FROM ConsultingStepEntity as c " + " where c.consultingId =:consultingId and c.visible=true order by c.orderNumber asc ")
    List<ConsultingStepEntity> getAllByConsultingId(@Param("consultingId") String consultingId);
}
