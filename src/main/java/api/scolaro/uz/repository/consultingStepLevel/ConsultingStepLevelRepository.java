package api.scolaro.uz.repository.consultingStepLevel;

import api.scolaro.uz.entity.ConsultingStepLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultingStepLevelRepository extends JpaRepository<ConsultingStepLevelEntity, String> {
    @Modifying
    @Transactional
    @Query("Update ConsultingStepLevelEntity set visible = false , deletedId=:deletedId, deletedDate=:deletedDate where id =:id")
    int deleted(@Param("id") String id,
                @Param("deletedId") String currentUserId,
                @Param("deletedDate") LocalDateTime now);

    @Query(" FROM ConsultingStepLevelEntity where consultingId =:consultingStepId and visible=true order by orderNumber asc ")
    List<ConsultingStepLevelEntity> getAllByConsultingStepId(@Param("consultingStepId") String consultingStepId);
}
