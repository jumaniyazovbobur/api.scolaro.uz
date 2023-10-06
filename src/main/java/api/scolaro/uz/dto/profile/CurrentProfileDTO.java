package api.scolaro.uz.dto.profile;

import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class CurrentProfileDTO {
    private String name;
    private String surname;
    private String phone;
    private List<RoleEnum> roleList;
    private AttachResponseDTO photo;
}
