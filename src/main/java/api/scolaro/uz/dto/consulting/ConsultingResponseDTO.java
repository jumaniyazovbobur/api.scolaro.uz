package api.scolaro.uz.dto.consulting;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.enums.GeneralStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConsultingResponseDTO {

    private String id;
    private String name;
    private String address;
    private String about;
    private String abbreviation;
    private AttachDTO photo;
    private LocalDateTime createdDate;
    private String phone;
    private String ownerName;
    private String ownerSurname;
    private GeneralStatus status;
}
