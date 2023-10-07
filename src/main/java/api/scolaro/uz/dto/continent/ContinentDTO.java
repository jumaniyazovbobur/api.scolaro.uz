package api.scolaro.uz.dto.continent;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ContinentDTO {
    private String nameUz;
    private String nameEn;
    private String nameRu;
    private Integer order;
    private LocalDateTime createdDate;
}
