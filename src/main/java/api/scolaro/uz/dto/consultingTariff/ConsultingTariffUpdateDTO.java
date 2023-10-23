package api.scolaro.uz.dto.consultingTariff;

import api.scolaro.uz.enums.ConsultingTarifType;
import api.scolaro.uz.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingTariffUpdateDTO {

    private String descriptionUz;
    private String descriptionRu;
    private String descriptionEn;
    private String name;
    private Double price;
    private GeneralStatus status;
    private ConsultingTarifType tariffType;
    private Integer order;
}
