package api.scolaro.uz.dto.consultingStep;

import api.scolaro.uz.dto.ConsultingStepLevel.ConsultingStepLevelDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultingStepDTO {
    private String id;
    private String name;
    private Integer orderNumber;
    private String description;
    private String consultingId;
    private List<ConsultingStepLevelResponseDTO> levelList;
}
