package api.scolaro.uz.repository.appApplication;

import api.scolaro.uz.entity.application.AppApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AppApplicationRepository extends JpaRepository<AppApplicationEntity, String> {

    Optional<AppApplicationEntity> findByIdAndVisibleTrue(String s);

    Optional<AppApplicationEntity> findByStudentIdAndConsultingIdAndVisibleTrue(String studentId, String consultingId);

    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set status = 'STARTED', startedDate = current_timestamp where id=:id")
    int statusToStarted(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set status = 'FINISHED', finishedDate = current_timestamp where id=:id")
    int statusToFinished(@Param("id") String id);

    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set status = 'CANCELED', canceledDate = current_timestamp where id=:id")
    int statusToCanceled(@Param("id") String id);


    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set consultingTariffId = :tariffId where id=:appId")
    int updateTariffId(@Param("appId") String appId, @Param("tariffId") String tariffId);

    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set consultingStepId = :conStepId where id=:applicationId")
    int updateConStepId(@Param("applicationId") String applicationId, @Param("conStepId") String conStepId);

    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set consultingStepLevelId = :consultingStepLevelId where id=:appId")
    int updateConsultingStepLevelId(@Param("appId") String appId, @Param("consultingStepLevelId") String consultingStepLevelId);

    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set consultingStepLevelStatusId = :consultingStepLevelStatusId where id=:appId")
    int updateConsultingStepLevelStatusId(@Param("appId") String appId, @Param("consultingStepLevelStatusId") String consultingStepLevelStatusId);


    @Query(value = "SELECT NEXTVAL('application_number_seq')",nativeQuery = true)
    Long getSequenceApplicationNumber();
}