package api.scolaro.uz.dto.program;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.country.CountryResponse;
import api.scolaro.uz.dto.destination.DestinationResponse;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.enums.ProgramRequirementType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramResponseFilterDTO {
    private Long programId;
    private String programTitle;
    private Date startDate;
    private Date endDate;
    private String studyFormat;
    private String studyMode;
    private String programType;
    private Long price;
    private String symbol;
    private AttachDTO attachDTO;
    private Boolean published;

    private UniversityResponseDTO university;
    private CountryResponse country;
    private DestinationResponse destination;

    private List<String> requirements;
}
