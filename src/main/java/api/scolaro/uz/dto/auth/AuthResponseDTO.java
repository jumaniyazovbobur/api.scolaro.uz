package api.scolaro.uz.dto.auth;

import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private String id;
    private String name;
    private String surname;
    private String jwt;
//    private AttachResponseDTO photo;
    private List<RoleEnum> roleList;
}
