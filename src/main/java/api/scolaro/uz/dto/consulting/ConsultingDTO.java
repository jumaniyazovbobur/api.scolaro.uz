package api.scolaro.uz.dto.consulting;

import api.scolaro.uz.dto.attach.AttachResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ConsultingDTO {

    private String id;
    private String name;
    private String phone;
    private String address;
    private String about;
    private AttachResponseDTO photo;
    private LocalDateTime createdDate;

}
