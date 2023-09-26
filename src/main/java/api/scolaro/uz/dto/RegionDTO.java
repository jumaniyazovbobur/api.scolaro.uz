package api.scolaro.uz.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RegionDTO {
    private Integer id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private LocalDateTime createdDate = LocalDateTime.now();
    private boolean visible = true;
}
