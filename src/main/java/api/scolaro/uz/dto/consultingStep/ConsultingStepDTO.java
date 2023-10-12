package api.scolaro.uz.dto.consultingStep;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingStepDTO {
    private String id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String orderNumber;
    private String description;
    private String consultingId;
}
