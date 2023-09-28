package api.scolaro.uz.dto.consulting;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingUpdateDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Owner name is required")
    private String ownerName;
    @NotBlank(message = "Owner surname is required")
    private String ownerSurname;
    private String about;
}
