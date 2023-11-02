package api.scolaro.uz.dto.ConsultingStepLevel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultingStepLevelResponseDTO {

    private String id;
    private String name;
    private Integer orderNumber;
    private String description;
    private String consultingStepId;
}
