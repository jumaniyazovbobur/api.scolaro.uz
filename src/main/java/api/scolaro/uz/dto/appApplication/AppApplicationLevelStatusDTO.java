package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationLevelStatusDTO {
    private String id;
    private String appApplicationId;
    private String consultingStepLevelId;
    private ApplicationStepLevelStatus applicationStepLevelStatus;
    private LocalDate deadline;
    private String description;
    private LocalDateTime createdDate;
    private Long amount;
}
