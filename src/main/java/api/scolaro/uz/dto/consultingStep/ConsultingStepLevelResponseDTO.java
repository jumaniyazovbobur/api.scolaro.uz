package api.scolaro.uz.dto.consultingStep;

import api.scolaro.uz.enums.StepLevelStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ConsultingStepLevelResponseDTO {
    private String id;
    private String name;
    private String description;
    private Integer orderNumber;
    private String consultingStepId;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private StepLevelStatus status;
    private String levelStatusList;
    private String levelAttachList;
}
