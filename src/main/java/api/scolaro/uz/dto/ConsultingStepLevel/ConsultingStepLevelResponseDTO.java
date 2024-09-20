package api.scolaro.uz.dto.ConsultingStepLevel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingStepLevelResponseDTO {

    private String id;
    private String name;
    private Integer orderNumber;
    private String description;
    private String consultingStepId;
}
