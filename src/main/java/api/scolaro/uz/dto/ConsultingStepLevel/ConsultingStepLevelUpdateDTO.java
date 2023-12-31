package api.scolaro.uz.dto.ConsultingStepLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingStepLevelUpdateDTO {
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Integer orderNumber;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
}
