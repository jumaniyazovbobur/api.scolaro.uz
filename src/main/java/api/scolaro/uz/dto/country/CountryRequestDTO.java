package api.scolaro.uz.dto.country;
// PROJECT NAME -> api.dachatop
// TIME -> 16:07
// MONTH -> 08
// DAY -> 06

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryRequestDTO {
    private String nameRu;
    private String nameEn;
    private String nameUz;
}
