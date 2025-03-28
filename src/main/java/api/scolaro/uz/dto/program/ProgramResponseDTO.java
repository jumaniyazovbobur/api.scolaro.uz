package api.scolaro.uz.dto.program;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.destination.DestinationLanguageResponse;
import api.scolaro.uz.dto.destination.DestinationResponse;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.enums.ProgramType;
import api.scolaro.uz.enums.StudyFormat;
import api.scolaro.uz.enums.StudyMode;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramResponseDTO {
    private Long id;
    private String title;
    private String titleUz;
    private String titleRu;
    private String titleEn;
    private String description;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String tuitionFeesDescription;
    private String tuitionFeesDescriptionUz;
    private String tuitionFeesDescriptionRu;
    private String tuitionFeesDescriptionEn;
    private String scholarshipDescription;
    private String scholarshipDescriptionUz;
    private String scholarshipDescriptionRu;
    private String scholarshipDescriptionEn;
    private String costOfLivingDescription;
    private String costOfLivingDescriptionUz;
    private String costOfLivingDescriptionRu;
    private String costOfLivingDescriptionEn;
    private DestinationLanguageResponse destinationLanguageResponse;
    private UniversityResponseDTO universityResponse;
    private AttachDTO attach;
    private LocalDate startDate;
    private LocalDate endDate;
    private StudyFormat studyFormat;
    private StudyMode studyMode;
    private ProgramType programType;
    private Long price;
    private String symbol;
    private List<ProgramRequirementType> programRequirementTypes;
}
