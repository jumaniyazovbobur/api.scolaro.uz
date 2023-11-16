package api.scolaro.uz.dto.university;

import api.scolaro.uz.dto.KeyValueDTO;
import api.scolaro.uz.dto.attach.AttachDTO;
import api.scolaro.uz.dto.country.CountryResponseDTO;
import api.scolaro.uz.enums.UniversityDegreeType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UniversityResponseFilterDTO {
    private Long id;
    private String name;
    private String webSite;
    private Long rating;
    private CountryResponseDTO country;
    private String description;
    private AttachDTO photo;
    private List<UniversityDegreeType> degreeTypeList;
    private List<KeyValueDTO> degreeList;
}
