package api.scolaro.uz.dto.ConsultingStepLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingStepLevelCreateDTO {
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String orderNumbers;
    private String description;
    private String consultingStepId;
    private String prtId;

}
