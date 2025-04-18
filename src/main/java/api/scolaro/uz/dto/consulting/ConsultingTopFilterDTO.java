package api.scolaro.uz.dto.consulting;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConsultingTopFilterDTO {
    private String countryName;
    private String name; // used in old version
    private Integer countryId;
}
