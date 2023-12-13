package api.scolaro.uz.dto.consulting;

import api.scolaro.uz.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private AttachDTO photo;
    private LocalDateTime createdDate;
}
