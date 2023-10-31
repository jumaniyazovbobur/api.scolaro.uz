package api.scolaro.uz.repository.consultingStepLevel;

import api.scolaro.uz.entity.consulting.ConsultingStepLevelEntity;
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

    @Query(" FROM ConsultingStepLevelEntity where consultingStepId =:consultingStepId and visible=true order by orderNumber asc  limit 1 ")
    ConsultingStepLevelEntity getFirstStepLevelByStepId(@Param("consultingStepId") String consultingStepId);

    @Query(value = " select *  from consulting_step_level where consulting_step_id =:consultingStepId " +
            "   and visible=true " +
            "   and order_number  <:currentStepLevelOrderNumber order by order_number desc limit 1)",
            nativeQuery = true)
    ConsultingStepLevelEntity getPreviousStepLevelByStepIdAndStepLevelOrderNumber(@Param("consultingStepId") String consultingStepId,
                                                                                  @Param("currentStepLevelOrderNumber") int currentStepLevelOrderNumber);

    @Query(value = " select *  from consulting_step_level where consulting_step_id =:consultingStepId " +
            "   and visible=true " +
            "   and order_number  >:currentStepLevelOrderNumber order by order_number asc limit 1)",
            nativeQuery = true)
    ConsultingStepLevelEntity getNextStepLevelByStepIdAndStepLevelOrderNumber(@Param("consultingStepId") String consultingStepId,
                                                                              @Param("currentStepLevelOrderNumber") int currentStepLevelOrderNumber);

    @Query(value = " select  count(*)  from consulting_step_level where consulting_step_id =:consultingStepId " +
            "   and visible=true  and  step_level_status !=: 'FINISHED'",
            nativeQuery = true)
    Long getApplicationNotFinishedStepLevelCount(@Param("consultingStepId") String consultingStepId);

}
