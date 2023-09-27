package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.entity.ConsultingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConsultingRepository extends JpaRepository<ConsultingEntity, String> {

    Optional<ConsultingEntity> findByIdAndVisibleTrue(String id);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set visible=true , deletedDate=:date where id=:id")
    void deleted(@Param("id") String id,
                 @Param("date") LocalDateTime date);

    Optional<ConsultingEntity> findByPhoneAndVisibleIsTrue(String phone);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set password =:nPswd where id =:id")
    int updatePassword(@Param("id") String id, @Param("nPswd") String nPswd);
}
