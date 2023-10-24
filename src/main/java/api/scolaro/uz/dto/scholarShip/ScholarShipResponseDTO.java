package api.scolaro.uz.dto.scholarShip;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.enums.DegreeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ScholarShipResponseDTO {
    private String id;
    private String name;
    private String description;
    private AttachDTO attach;
    private LocalDate expiredDate;
    private DegreeType degreeType;
    private LocalDateTime createdDate;
}
