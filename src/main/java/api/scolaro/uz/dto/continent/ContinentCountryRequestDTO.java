package api.scolaro.uz.dto.continent;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContinentCountryRequestDTO {
    @NotNull(message = "Continent id required")
    private Long continentId;
    @NotNull(message = "Country id required")
    private Long countryId;
}
