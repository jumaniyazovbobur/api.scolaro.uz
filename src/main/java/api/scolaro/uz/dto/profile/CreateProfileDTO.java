package api.scolaro.uz.dto.profile;


import api.scolaro.uz.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProfileDTO {
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Phone required")
    private String phone;
    @NotBlank(message = "Surname required")
    private String surname;
    @NotBlank(message = "Password required")
    private String password;
    @NotBlank(message = "Role required")
    private List<RoleEnum> roles;
    private Integer facultyId;
}
