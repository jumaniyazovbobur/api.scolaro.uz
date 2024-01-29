package api.scolaro.uz.dto.profile;


import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileResponseDTO {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private AttachDTO photo;
    private LocalDateTime createdDate;
    private GeneralStatus status;
    private List<RoleEnum> roles;
}
