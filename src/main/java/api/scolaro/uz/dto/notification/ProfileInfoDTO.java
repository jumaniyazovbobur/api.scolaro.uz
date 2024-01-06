package api.scolaro.uz.dto.notification;

import api.scolaro.uz.enums.transaction.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 'Mukhtarov Sarvarbek' on 27.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
public class ProfileInfoDTO {
    private String toProfile;
    private ProfileType toType;
    private String fromProfile;
    private ProfileType fromType;
}
