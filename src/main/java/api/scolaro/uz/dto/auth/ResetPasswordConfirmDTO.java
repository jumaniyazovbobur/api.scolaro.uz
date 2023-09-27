package api.scolaro.uz.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordConfirmDTO {
    @NotBlank(message = "Phone required")
    private String phone;
    @NotBlank(message = "Sms code required")
    private String smsCode;
    @NotBlank(message = "Password required")
    @Size(min = 6, message = "Password size min 6 chars.")
    private String newPassword;
    @NotBlank(message = "Confirm password required")
    @Size(min = 6, message = "Password size min 6 chars.")
    private String repeatNewPassword;
}
