package api.scolaro.uz.dto.consultingProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Admin on 23.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.dto.consultingProfile
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultingProfileCreateDTO {
    private String name;

    private String surname;

    private String phone;

    private String password;

    private String photoId;

    private Long countryId;

    private String address;

    private String consultingId;
}
