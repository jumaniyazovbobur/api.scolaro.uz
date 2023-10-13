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

    private String nameUz;
    private String nameRu;
    private String nameEn;
    private String description;
    private Double price;
    private GeneralStatus status;
    private ConsultingTarifType tariffType;
    private Integer order;
}
