package api.scolaro.uz.dto.notification;

import api.scolaro.uz.enums.notification.NotificationType;
import com.google.gson.Gson;
import lombok.*;

import java.util.*;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationDTO {
    private String title;
    private String body;
    private String imageUrl;
    private List<String> registrationIds = new ArrayList<>();
    private Map<String, String> data = new HashMap<>();
    private List<ProfileInfoDTO> profiles;
    private NotificationType type;

    {
        data.put("click_action", "FLUTTER_NOTIFICATION_CLICK");
    }

    public String toJson() {
        Map<String, Object> res = new HashMap<>();

        res.put("registration_ids", this.registrationIds);
        res.put("notification", Map.of(
                "body", this.body,
                "title", this.title,
                "image", this.imageUrl != null ? this.imageUrl : "",
                "android_channel_id","scolaro_notification_id"
        ));
        res.put("data", this.data);
        res.put("priority", "high");
        return new Gson()
                .toJson(res);
    }
}
