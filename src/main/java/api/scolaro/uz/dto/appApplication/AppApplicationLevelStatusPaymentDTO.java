package api.scolaro.uz.dto.appApplication;

import jakarta.validation.constraints.NotBlank;

public class AppApplicationLevelStatusPaymentDTO {
    @NotBlank(message = "applicationId required")
    private String appApplicationId;
    @NotBlank(message = "Consulting stepLevelId required")
    private String consultingStepLevelId;
}
