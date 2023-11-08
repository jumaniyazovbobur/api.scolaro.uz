package api.scolaro.uz.dto.ConsultingStepLevel;

import api.scolaro.uz.enums.StepLevelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingStepLevelDTO {
    private String id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String name;
    private Integer orderNumber;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String description;
    private String consultingStepId;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private StepLevelStatus status;
    private String levelStatusList;
    private String levelAttachList;
}

