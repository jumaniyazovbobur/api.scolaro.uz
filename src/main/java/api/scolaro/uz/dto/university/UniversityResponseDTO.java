package api.scolaro.uz.dto.university;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UniversityResponseDTO {
    private String name;
    private String webSite;
    private Long rating;
    private Long countryId;
    private String description;

}
