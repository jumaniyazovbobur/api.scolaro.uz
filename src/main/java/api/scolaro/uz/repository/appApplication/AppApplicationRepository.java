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

    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set status = :status where id=:id")
    int changeStatus(@Param("id") String id,
                     @Param("status") AppStatus status);

    @Transactional
    @Modifying
    @Query("update AppApplicationEntity set status = :status, finishedDate=:date where id=:id")
    int changeStatus(@Param("id") String id,
                     @Param("status") AppStatus status,
                     @Param("date") LocalDateTime date);
}
