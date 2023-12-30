package api.scolaro.uz.repository.notification;

import api.scolaro.uz.entity.notification.NotificationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistoryEntity, String> {
    @Query("update NotificationHistoryEntity set isRead = ?2 where id = ?1")
    @Transactional
    @Modifying
    void updateIsRead(String id, boolean b);
}