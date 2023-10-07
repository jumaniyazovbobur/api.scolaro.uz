package api.scolaro.uz.dto.continent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContinentRequestDTO {
    @NotBlank(message = "Uzbek name required")
    private String nameUz;
    @NotBlank(message = "English name required")
    private String nameEn;
    @NotBlank(message = "Russian name required")
    private String nameRu;
    @NotNull(message = "Order number required")
    private Integer order;

}
