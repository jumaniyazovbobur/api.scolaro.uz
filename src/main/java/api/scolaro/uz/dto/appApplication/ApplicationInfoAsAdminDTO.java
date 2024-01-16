package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * @author 'Mukhtarov Sarvarbek' on 16.01.2024
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationInfoAsAdminDTO {
    String id;
    Long applicationNumber;
    AppStatus status;
    LocalDateTime createdDate;
}
