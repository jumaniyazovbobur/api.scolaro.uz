package api.scolaro.uz.repository.notification;

import api.scolaro.uz.entity.notification.NotificationHistoryEntity;
import api.scolaro.uz.enums.notification.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistoryEntity, String> {
    @Query("update NotificationHistoryEntity set isRead = ?2 where id = ?1")
    @Transactional
    @Modifying
    void updateIsRead(String id, boolean b);

    Page<NotificationHistoryEntity> findAllByIsReadIsFalseAndToProfileId(String toProfileId, Pageable pageable);

    Long countByIsReadIsFalseAndToProfileId(String toProfileId);

    @Query("update NotificationHistoryEntity set isRead = ?1 where toProfileId = ?3 and type = ?2")
    @Transactional
    @Modifying
    int updateIsReadByType(boolean b,NotificationType notificationType, String currentUserId);
}