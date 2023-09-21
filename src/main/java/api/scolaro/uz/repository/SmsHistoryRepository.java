package api.scolaro.uz.repository;

import api.scolaro.uz.entity.sms.SmsHistoryEntity;
import api.scolaro.uz.enums.sms.SmsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SmsHistoryRepository extends JpaRepository<SmsHistoryEntity, String> {
    Long countByPhoneAndCreatedDateBetween(String phone, LocalDateTime toTime, LocalDateTime fromTime);

    Optional<SmsHistoryEntity> findByPhoneAndStatus(String phone, SmsStatus type);

    Optional<SmsHistoryEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    @Modifying
    @Transactional
    @Query("update sms_history set status = ?2 where id = ?1")
    void updateStatus(String id, SmsStatus status);

    @Modifying
    @Transactional
    @Query("update sms_history set smsCount = smsCount + 1 where id = ?1")
    void increaseCount(String id);
}
