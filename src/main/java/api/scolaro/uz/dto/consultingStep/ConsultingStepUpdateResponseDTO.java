package api.scolaro.uz.dto.consultingStep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingStepUpdateResponseDTO {
    private String id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Integer orderNumber;
    private String description;
}
