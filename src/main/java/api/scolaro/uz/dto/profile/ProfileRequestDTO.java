package api.scolaro.uz.dto.profile;

import api.scolaro.uz.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@ToString
public class ProfileRequestDTO {
    @NotBlank(message = "name")
    private String name;
    @NotBlank(message = "surname")
    private String surname;
    @NotBlank(message = "login")
    private String login;
    @NotBlank(message = "pass")
    private String password;
    @NotBlank(message = "contact")
    private String contact;
    @NotNull(message = "role")
    private List<RoleEnum> roleList;
}
