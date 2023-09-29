package api.scolaro.uz.dto.scholarShip;

import api.scolaro.uz.enums.DegreeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
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
    @NotNull(message = "Degree Type required")
    private DegreeType degreeType;


}
