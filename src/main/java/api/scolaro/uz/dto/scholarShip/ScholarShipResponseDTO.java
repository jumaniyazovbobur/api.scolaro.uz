package api.scolaro.uz.dto.scholarShip;

import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.attach.AttachResponseDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.dto.university.UniversityResponseFilterDTO;
import api.scolaro.uz.enums.DegreeType;
import api.scolaro.uz.enums.UniversityDegreeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ScholarShipResponseDTO {
    private String id;
    private String name;
    private String description;
    private AttachDTO attach;
    private LocalDate expiredDate;
    private LocalDate startDate;
    private Integer price;
    private Long universityId;
    private UniversityResponseDTO university;
    private List<KeyValueDTO> degreeTypeList;
    private LocalDateTime createdDate;
    private List<ConsultingResponseDTO> consultingList;
}
