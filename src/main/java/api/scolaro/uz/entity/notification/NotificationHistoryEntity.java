package api.scolaro.uz.entity.notification;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.notification.NotificationType;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.service.notification.MapToStringConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Entity
@Table(name = "notification_history")
@Getter
@Setter
public class NotificationHistoryEntity extends BaseEntity {
    private String title;

    private String body;

    private String toProfileId;

    private String fromProfileId;

    @Enumerated(EnumType.STRING)
    private ProfileType toProfileType;

    @Enumerated(EnumType.STRING)
    private ProfileType fromProfileType;

    private String firebaseToken;

    private NotificationType type;

    private Boolean isRead = false;

    @Convert(converter = MapToStringConverter.class)
    @Column(columnDefinition = "text")
    private Map<String, String> data;
}
