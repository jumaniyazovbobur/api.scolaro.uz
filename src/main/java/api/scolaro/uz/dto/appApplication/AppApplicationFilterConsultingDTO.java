package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import api.scolaro.uz.enums.StepLevelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationFilterConsultingDTO {
    private String query; // query
    private AppStatus status;
    private ApplicationStepLevelStatus applicationStepLevelStatus;
}
