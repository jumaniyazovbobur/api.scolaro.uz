package api.scolaro.uz.dto.university;

import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
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
    private AttachDTO photo;
    private List<UniversityDegreeType> degreeTypeList;
    private List<KeyValueDTO> degreeList;
    private CountryResponseDTO country;
}
