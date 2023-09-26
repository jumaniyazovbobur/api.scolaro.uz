package api.scolaro.uz.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    @NotBlank(message = "Old password required")
    private String oldPassword;
    @NotBlank(message = "New password required")
    private String newPassword;
    @NotBlank(message = "New password confirm required")
    private String confirmPassword;
}
