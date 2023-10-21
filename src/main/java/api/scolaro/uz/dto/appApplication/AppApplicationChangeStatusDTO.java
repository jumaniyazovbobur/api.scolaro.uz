package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.enums.AppStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppApplicationChangeStatusDTO {

    @NotNull(message = "status required")
    private AppStatus status;
}
