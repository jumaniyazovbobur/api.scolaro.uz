package api.scolaro.uz.dto.program;

import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.dto.destination.DestinationLanguageResponse;
import api.scolaro.uz.dto.destination.DestinationResponse;
import api.scolaro.uz.dto.university.UniversityResponseDTO;
import api.scolaro.uz.enums.ProgramRequirementType;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private DestinationLanguageResponse destinationLanguageResponse;
    private UniversityResponseDTO universityResponse;
    private AttachDTO attach;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<ProgramRequirementType> programRequirementTypes;
}
