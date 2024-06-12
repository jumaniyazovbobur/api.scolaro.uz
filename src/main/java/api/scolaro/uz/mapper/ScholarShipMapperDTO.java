package api.scolaro.uz.mapper;

import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class ScholarShipMapperDTO {
    private String id;
    private String scholarShipName;
    private String description;
    private String abbreviation;
    private Integer price;
    private LocalDate startDate;
    private LocalDate expiredDate;
    private AttachDTO attachDTO;
    private UniversityResponseDTO university;
    private List<KeyValueDTO> degreeTypeList;
}
