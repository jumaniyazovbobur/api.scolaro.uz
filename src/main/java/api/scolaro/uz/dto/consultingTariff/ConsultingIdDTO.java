package api.scolaro.uz.dto.consultingTariff;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingIdDTO {

    @NotBlank(message = "consulting Id required ")
    private String consultingId;
}
