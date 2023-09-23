package api.scolaro.uz.repository.profile;

import api.scolaro.uz.entity.profile.ConsultingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ConsultingRepository extends JpaRepository<ConsultingEntity, String> {

    Optional<ConsultingEntity> findByIdAndVisibleTrue(String id);
    @Transactional
    @Modifying
    @Query("update ConsultingEntity set visible=true , deletedDate=now() where id=:id")
    void deleted(String id);
}
