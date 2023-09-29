package api.scolaro.uz.dto.university;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UniversityUpdateDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Website is required")
    private String webSite;
    @NotNull(message = "Rating is required")
    private Long rating;
    @NotNull(message = "Country is required")
    private Long countryId;
    private String description;
}
