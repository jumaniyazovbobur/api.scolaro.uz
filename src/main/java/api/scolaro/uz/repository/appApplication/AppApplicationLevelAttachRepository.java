package api.scolaro.uz.repository.appApplication;

import api.scolaro.uz.entity.application.AppApplicationLevelAttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AppApplicationLevelAttachRepository extends JpaRepository<AppApplicationLevelAttachEntity, String> {
    Optional<AppApplicationLevelAttachEntity> findByAttachId(String attachId);

    @Transactional
    @Modifying
    @Query("update AppApplicationLevelAttachEntity  set visible = false where attachId =:attachId")
    int deleteByAttachId(@Param("attachId") String attachId);
}
