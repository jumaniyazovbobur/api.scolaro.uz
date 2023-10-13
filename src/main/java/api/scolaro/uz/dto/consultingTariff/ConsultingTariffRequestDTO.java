package api.scolaro.uz.dto.consultingTariff;

import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.ConsultingTarifType;
import api.scolaro.uz.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultingTariffRequestDTO {

    @NotBlank(message = "Uzbek name required")
    private String nameUz;
    @NotBlank(message = "Russian name required")
    private String nameRu;
    @NotBlank(message = "English name required")
    private String nameEn;
    @NotNull(message = "Description required")
    private String description;
    @NotNull(message = "Price required")
    private Double price;
    @NotBlank(message = "Consulting id required")
    private String consultingId;
    @NotNull(message = "Status required")
    private GeneralStatus status;
    @NotNull(message = "Tariff Type required")
    private ConsultingTarifType tariffType;
    @NotNull(message = "Order  required")
    private Integer order;
}
