package api.scolaro.uz.dto.consulting;

import api.scolaro.uz.enums.GeneralStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingStatusUpdateDTO {
    @NotBlank(message = "Name is required")
    private GeneralStatus status;
}
