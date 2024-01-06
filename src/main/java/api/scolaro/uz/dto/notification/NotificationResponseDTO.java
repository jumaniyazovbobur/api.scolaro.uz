package api.scolaro.uz.dto.notification;

import api.scolaro.uz.entity.notification.NotificationHistoryEntity;
import api.scolaro.uz.enums.notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Admin on 30.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.dto.notification
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
public class NotificationResponseDTO {
    private String id;
    private String title;
    private String body;
    private NotificationType type;

    public static NotificationResponseDTO toDTO(NotificationHistoryEntity entity) {
        return new NotificationResponseDTO(entity.getId(), entity.getTitle(), entity.getBody(), entity.getType());
    }
}
