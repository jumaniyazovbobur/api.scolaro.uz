package api.scolaro.uz.repository.appApplication;

import api.scolaro.uz.entity.AppApplicationEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.enums.GeneralStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
}
