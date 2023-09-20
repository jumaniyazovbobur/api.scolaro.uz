package api.scolaro.uz.dto.profile;


import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private String id;
    private String name;
    private String phone;
    private String surname;
    private String password;
    private LocalDateTime createdDate;
    private GeneralStatus status;
    private Integer facultyId;
    private List<RoleEnum> roles;

}
