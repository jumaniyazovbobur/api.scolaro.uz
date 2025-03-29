package api.scolaro.uz.dto.program;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.destination.DestinationLanguageResponse;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.enums.ProgramType;
import api.scolaro.uz.enums.StudyFormat;
import api.scolaro.uz.enums.StudyMode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramFilterDTO {
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private Long destinationId;
    private Long universityId;
    private Long countryId;
    private String programType;
    private String studyFormat;
    private String studyMode;
    private LocalDate endDate;
}
