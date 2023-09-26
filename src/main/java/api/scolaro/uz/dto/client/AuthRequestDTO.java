package api.scolaro.uz.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequestDTO {
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Surname required")
    private String surname;
    @NotBlank(message = "phoneNumber required")
    @Size(min = 9, max = 13, message = "phoneNumber not valid")
    private String phoneNumber;
    @NotBlank(message = "Password required")
    private String password;
}
