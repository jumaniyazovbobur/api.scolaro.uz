package api.scolaro.uz.dto.scholarShip;

import api.scolaro.uz.enums.DegreeType;
import api.scolaro.uz.enums.UniversityDegreeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScholarShipRequestDTO {
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "description required")
    private String description;
    @NotBlank(message = "photo required")
    private String photoId;
    @NotNull(message = "Expired date required")
    private LocalDate expiredDate;
    @NotNull(message = "Start date required")
    private LocalDate startDate;
    @NotNull(message = "University is required")
    private Long universityId;
    @NotNull(message = "price is required")
    private Integer price;
    @NotNull(message = "degreeTypeList required")
    private List<UniversityDegreeType> degreeTypeList;


}
