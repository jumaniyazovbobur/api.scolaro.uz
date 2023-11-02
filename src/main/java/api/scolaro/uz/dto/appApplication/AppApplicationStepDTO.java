package api.scolaro.uz.dto.appApplication;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppApplicationStepDTO {
    @NotBlank(message = "ConsultingStepId id is required")
    private String consultingStepId;
}
