package api.scolaro.uz.dto.consulting;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.enums.AppLanguage;
import api.scolaro.uz.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private ConsultingResponseDTO consulting;
    private List<RoleEnum> roleList;
    private AppLanguage lang;
}

