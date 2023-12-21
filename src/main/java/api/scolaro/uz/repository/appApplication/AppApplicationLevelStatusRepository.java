package api.scolaro.uz.repository.appApplication;

import api.scolaro.uz.entity.application.AppApplicationLevelStatusEntity;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface AppApplicationLevelStatusRepository extends CrudRepository<AppApplicationLevelStatusEntity, String> {
    @Transactional
    @Modifying
    @Query("update AppApplicationLevelStatusEntity  set applicationStepLevelStatus=:applicationStepLevelStatus, paymentDate=:paymentDate  where id =:id")
    void updateApplicationStepLevelStatus(@Param("id") String id,
                                          @Param("applicationStepLevelStatus") ApplicationStepLevelStatus applicationStepLevelStatus,
                                          @Param("paymentDate") LocalDateTime paymentDate);
}
