package api.scolaro.uz.dto.university;

import api.scolaro.uz.enums.UniversityDegreeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String photoId;
    private String logoId;
    private List<UniversityDegreeType> degreeList;
    private List<String> facultyList;
}
