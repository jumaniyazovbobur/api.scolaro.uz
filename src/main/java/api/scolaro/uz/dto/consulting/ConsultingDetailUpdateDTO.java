package api.scolaro.uz.dto.consulting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingDetailUpdateDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @NotNull(message = "Photo is required")
    private String photoId;
    private String about;
    @NotNull(message = "Abbreviation is required")
    private String abbreviation;
    @NotNull(message = "OrderNumber is required")
    private Integer orderNUmber;
}
