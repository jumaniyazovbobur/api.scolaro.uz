package api.scolaro.uz.dto.ConsultingStepLevel;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingStepLevelCreateDTO {
    @NotBlank(message = "nameUz is required")
    private String nameUz;
    @NotBlank(message = "nameEn is required")
    private String nameEn;
    @NotBlank(message = "nameRu is required")
    private String nameRu;
    @NotBlank(message = "Order is required")
    private String orderNumber;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank
    private String consultingStepId;
}
