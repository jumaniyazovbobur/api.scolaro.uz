package api.scolaro.uz.dto.consultingStep;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingStepCreateDTO {
    @NotBlank(message = "name is required")
    private String name;
    @NotNull(message = "Order is required")
    private Integer orderNumber;
    @NotBlank(message = "Description is required")
    private String description;
}
