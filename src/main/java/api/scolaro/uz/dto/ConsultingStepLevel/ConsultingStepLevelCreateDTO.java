package api.scolaro.uz.dto.ConsultingStepLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Order is required")
    private Integer orderNumber;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank
    private String consultingStepId;
}
