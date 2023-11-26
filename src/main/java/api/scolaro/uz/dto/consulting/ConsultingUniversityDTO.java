package api.scolaro.uz.dto.consulting;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingUniversityDTO {
    @NotNull
    private Long universityId;
//    @NotBlank
//    private String tariffId;
}
