package api.scolaro.uz.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    @NotBlank(message = "Old password required")
    @Size(min = 6,message = "Password min 6 character")
    private String oldPassword;
    @NotBlank(message = "New password required")
    @Size(min = 6,message = "Password min 6 character")
    private String newPassword;
    @NotBlank(message = "New password confirm required")
    @Size(min = 6,message = "Password min 6 character")
    private String confirmPassword;
}
