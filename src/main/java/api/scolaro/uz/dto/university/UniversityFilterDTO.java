package api.scolaro.uz.dto.university;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UniversityFilterDTO {
    private String name;
    private Long countryId;
    private Long rating;
}
