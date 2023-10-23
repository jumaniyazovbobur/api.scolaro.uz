package api.scolaro.uz.dto.consulting;


import api.scolaro.uz.dto.attach.AttachDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ConsultingDTO {
    private String id;
    private String name;
    private String phone;
    private String password;
    private String address;
    private String about;
    private AttachDTO photo;
}
