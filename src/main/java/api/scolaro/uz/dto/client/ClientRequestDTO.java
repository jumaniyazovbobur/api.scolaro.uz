package api.scolaro.uz.dto.client;

import api.scolaro.uz.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientRequestDTO {
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "phoneNumber required")
    @Size(min = 9, max = 13, message = "phoneNumber not valid")
    private String phoneNumber;
    @NotBlank(message = "Surname required")
    private String surname;
    @NotBlank(message = "Password required")
    private String password;

}
