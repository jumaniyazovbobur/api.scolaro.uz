package api.scolaro.uz.dto.consultingStep;

import api.scolaro.uz.enums.StepType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultingStepResponseDTO {
    private String id;
    private String name;
    private Integer orderNumber;
    private StepType type;
    private String description;
    private String consultingId;
}
