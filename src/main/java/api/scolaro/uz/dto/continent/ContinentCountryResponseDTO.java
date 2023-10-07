package api.scolaro.uz.dto.continent;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContinentCountryResponseDTO {
    private Long continentId;
    private Long countryId;
    private LocalDateTime createdDate;
}
