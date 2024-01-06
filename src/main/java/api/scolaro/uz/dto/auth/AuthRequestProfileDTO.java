package api.scolaro.uz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequestProfileDTO {
    @NotNull(message = "Login required")
    private String phone;

    @NotNull(message = "Password required")
    @Size(min = 6, max = 20, message = "About Me must between 6 and 20 characters")
    private String password;

    private String firebaseId;
}