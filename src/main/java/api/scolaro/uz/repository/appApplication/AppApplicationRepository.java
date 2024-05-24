package api.scolaro.uz.repository.appApplication;

import api.scolaro.uz.entity.application.AppApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppApplicationRepository extends JpaRepository<AppApplicationEntity, String> {

    Optional<AppApplicationEntity> findByIdAndVisibleTrue(String s);

    List<AppApplicationEntity> findByStudentIdAndConsultingIdAndVisibleTrue(String studentId, String consultingId);

    @Query("FROM AppApplicationEntity a join fetch a.consulting join fetch a.consultingStepLevel where a.studentId = ?1 order by a.createdDate desc ")
    List<AppApplicationEntity> findByStudentIdAndVisibleTrue(String studentId);

    Optional<AppApplicationEntity> findByStudentIdAndConsultingIdAndUniversityIdAndVisibleTrue(String studentId, String consultingId, Long universityId);

    @Query(" from AppApplicationEntity a where a.status = 'TRAIL' and a.studentId =:studentId and a.consultingId =:consultingId and a.universityId is null and a.visible=true")
    Optional<AppApplicationEntity> getStatusTrailApplicationWithNoUniversity(@Param("studentId") String studentId, @Param("consultingId") String consultingId);

    @Query("""
            from AppApplicationEntity a
            left join fetch a.consulting
            left join fetch a.university
            left join fetch a.student
            where a.id = ?1 and a.visible = true
            """)
    Optional<AppApplicationEntity> findAllDataByIdAndVisibleIsTrue(String applicationId);

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


    @Query(value = "SELECT NEXTVAL('application_number_seq')", nativeQuery = true)
    Long getSequenceApplicationNumber();

    @Query("update AppApplicationEntity set consultingProfileId = ?2 where id = ?1")
    @Transactional
    @Modifying
    int updateApplicationConsultingProfile(String applicationId, String newProfileId);

    @Query("update AppApplicationEntity set universityId =:universityId where id =:appId")
    @Transactional
    @Modifying
    int updateApplicationUniversityId(@Param("appId") String appId, @Param("universityId") Long universityId);

    @Query(value = "select get_admin_dashboard_data()", nativeQuery = true)
    String getAdminDashboardData();

    @Query(value = "select get_consulting_dashboard_data(?1)", nativeQuery = true)
    String getConsultingDashboardData(String consultingId);
}