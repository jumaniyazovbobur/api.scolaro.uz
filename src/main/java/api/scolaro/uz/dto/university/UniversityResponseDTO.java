package api.scolaro.uz.dto.university;

import api.scolaro.uz.dto.FacultyDTO;
import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.consulting.ConsultingResponseDTO;
import api.scolaro.uz.dto.country.CountryDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.enums.UniversityDegreeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UniversityResponseDTO {
    private Long id;
    private String name;
    private String webSite;
    private Long rating;
    private Long countryId;
    private String description;
    private String abbreviation;
    private AttachDTO photo;
    private AttachDTO logo;
    private List<UniversityDegreeType> degreeTypeList;
    private List<KeyValueDTO> degreeList;
    private CountryResponseDTO country;
    private List<FacultyDTO> facultyList;
    private List<String> facultyIdList;
    private List<ConsultingResponseDTO> consultingList;
    private Long studentCount;
}
