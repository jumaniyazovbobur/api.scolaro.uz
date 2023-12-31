package api.scolaro.uz.dto.consultingTariff;

import api.scolaro.uz.enums.ConsultingTariffType;
import api.scolaro.uz.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingTariffResponseDTO {
    private String id;
    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String description;
    private String name;
    private Double price;
    private String consultingId;
    private GeneralStatus status;
    private ConsultingTariffType tariffType;
    private Integer orderNumber;

}
