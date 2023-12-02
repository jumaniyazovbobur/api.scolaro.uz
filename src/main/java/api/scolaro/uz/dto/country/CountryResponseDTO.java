package api.scolaro.uz.dto.country;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CountryResponseDTO extends CountryRequestDTO {
    private Long id;
    private String name;
    private Integer universityCount;
}
