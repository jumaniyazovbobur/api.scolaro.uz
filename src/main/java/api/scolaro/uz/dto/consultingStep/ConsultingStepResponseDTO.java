package api.scolaro.uz.dto.consultingStep;

import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelResponseDTO;
import api.scolaro.uz.enums.StepType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
    private List<ConsultingStepLevelDTO> levelList;
}
