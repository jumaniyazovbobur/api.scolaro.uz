package api.scolaro.uz.dto.country;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


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
