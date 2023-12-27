package api.scolaro.uz.repository.notification;

import api.scolaro.uz.entity.notification.NotificationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistoryEntity, String> {
}