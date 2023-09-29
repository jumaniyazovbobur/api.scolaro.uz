package api.scolaro.uz.dto.scholarShip;

import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.enums.DegreeType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ScholarShipUpdateDTO {
    private String name;
    private String description;
    private AttachResponseDTO attach;
    private LocalDate expiredDate;
    private DegreeType degreeType;
}
