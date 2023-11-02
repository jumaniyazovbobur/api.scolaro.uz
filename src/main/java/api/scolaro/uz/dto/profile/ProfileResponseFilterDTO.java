package api.scolaro.uz.dto.profile;

import api.scolaro.uz.dto.PersonRoleDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileResponseFilterDTO {
    private String id;
    private String name;
    private String surname;
    private AttachDTO attach;
    private PersonRoleDTO personRole;
}
