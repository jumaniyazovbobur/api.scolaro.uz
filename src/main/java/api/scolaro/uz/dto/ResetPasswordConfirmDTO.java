package api.scolaro.uz.dto;

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
    @Size(message = "Password size min 6 chars.")
    private String newPassword;
    @NotBlank(message = "Confirm password required")
    @Size(message = "Password size min 6 chars.")
    private String repeatNewPassword;
}
