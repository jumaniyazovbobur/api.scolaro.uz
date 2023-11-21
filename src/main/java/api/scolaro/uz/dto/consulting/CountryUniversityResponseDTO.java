package api.scolaro.uz.dto.consulting;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryUniversityResponseDTO {
    private String id; // countryId
    private String name; // countryName
    private String universityList; // university json list

}
