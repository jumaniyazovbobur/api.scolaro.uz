package api.scolaro.uz.dto.country;
// PROJECT NAME -> api.dachatop
// TIME -> 10:56
// MONTH -> 08
// DAY -> 09

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDTO {

    protected Long id;
    private String key;
    private String name;
    private String logoId;
    private Boolean visible;
    private LocalDateTime createdDate;
    private String nameRu;
    private String nameLatin;
    private String nameCyrillic;



}
