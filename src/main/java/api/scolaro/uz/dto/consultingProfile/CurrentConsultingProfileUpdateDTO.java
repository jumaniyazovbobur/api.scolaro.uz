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
public class CurrentConsultingProfileUpdateDTO {
    private String name;

    private String surname;

    private String photoId;

}
