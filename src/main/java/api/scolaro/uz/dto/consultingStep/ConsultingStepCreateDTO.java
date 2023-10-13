package api.scolaro.uz.dto.consultingStep;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingStepCreateDTO {
    private String  id;
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
    @NotNull(message = "Consulting is required")
    private String consultingId;


}
