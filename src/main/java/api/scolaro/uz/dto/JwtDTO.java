package api.scolaro.uz.dto;



import api.scolaro.uz.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtDTO {
    private String id;
    private String phone;
    private List<String> roleList;

    public JwtDTO(String id, String phone, List<String> roleList) {
        this.id = id;
        this.phone = phone;
        this.roleList = roleList;
    }

}
