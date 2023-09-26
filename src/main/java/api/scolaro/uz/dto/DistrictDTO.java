package api.scolaro.uz.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistrictDTO {
    private Integer id;
    private Integer regionId;
    private RegionDTO region;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private LocalDateTime createdDate = LocalDateTime.now();
    private boolean visible = true;
    private String county;
    private String name;


}
