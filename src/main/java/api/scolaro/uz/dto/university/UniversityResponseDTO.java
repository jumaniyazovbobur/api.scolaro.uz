package api.scolaro.uz.dto.university;

import api.scolaro.uz.dto.attach.AttachDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

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
}
