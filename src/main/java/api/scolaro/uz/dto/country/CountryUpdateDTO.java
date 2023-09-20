package api.scolaro.uz.dto.country;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


// PROJECT NAME -> api.dachatop
// TIME -> 11:50
// MONTH -> 08
// DAY -> 09
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryUpdateDTO {
    @NotBlank(message = "Uzbek name required")
    private String nameUz;
    @NotBlank(message = "English name required")
    private String nameEn;
    @NotBlank(message = "Russian name required")
    private String nameRu;

}
