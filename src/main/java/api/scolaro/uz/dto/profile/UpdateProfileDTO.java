package api.scolaro.uz.dto.profile;


import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDTO {
    private String name;
    private String phone;
    private String surname;
    private String password;
    private GeneralStatus status = GeneralStatus.ACTIVE;
    private RoleEnum role;
    private Integer facultyId;
}
