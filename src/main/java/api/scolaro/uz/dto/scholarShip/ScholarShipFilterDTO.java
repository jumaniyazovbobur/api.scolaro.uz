package api.scolaro.uz.dto.scholarShip;

import api.scolaro.uz.enums.DegreeType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ScholarShipFilterDTO {
    private String universityName;
    private String name;
//    private Boolean visible;
//    private LocalDate expiredDate;
//    private DegreeType degreeType;
//    private LocalDate dateFrom;
//    private LocalDate dateTo;
}
