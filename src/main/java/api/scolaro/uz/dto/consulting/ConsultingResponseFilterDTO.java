package api.scolaro.uz.dto.consulting;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingResponseFilterDTO {
    private String id;
    private String name;
    private AttachDTO attach;
}
