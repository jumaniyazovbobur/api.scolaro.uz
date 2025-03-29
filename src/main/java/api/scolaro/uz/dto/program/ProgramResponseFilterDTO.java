package api.scolaro.uz.dto.program;

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
    private String attachProgramId;
    private Boolean published;

    private Long universityId;
    private String universityName;
    private String attachUniversityLogo;

    private Long countryId;
    private String countryName;
    private String attachCountry;

    private Long destinationId;
    private String destinationName;
    private String attachDestinationId;
    private Boolean showInMainPageDestination;

    private List<String> requirements;
}
