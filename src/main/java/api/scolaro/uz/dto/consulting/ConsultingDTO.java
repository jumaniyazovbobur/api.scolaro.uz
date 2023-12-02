package api.scolaro.uz.dto.consulting;


import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingDTO {
    private String id;
    private String name;
    private String phone;
    private String password;
    private String address;
    private String about;
    private AttachDTO photo;
    private List<RoleEnum> roleList;
}
