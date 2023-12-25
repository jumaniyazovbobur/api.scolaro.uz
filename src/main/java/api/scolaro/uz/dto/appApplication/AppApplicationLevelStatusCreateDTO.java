package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import api.scolaro.uz.enums.StepLevelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationLevelStatusCreateDTO {
    @NotBlank(message = "applicationId required")
    private String appApplicationId;
    @NotBlank(message = "Consulting stepLevelId required")
    private String consultingStepLevelId;
    @NotBlank(message = "Status required")
    private ApplicationStepLevelStatus applicationStepLevelStatus;
    private LocalDate deadline;
    private String description;
    private Long amount; // in sum. (not in tiyin)
}
