package api.scolaro.uz.dto.consultingProfile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Surname is required!")
    private String surname;

    @NotBlank(message = "Phone is required!")
    private String phone;

    private String photoId;

    private Long countryId;

    private String address;
}
