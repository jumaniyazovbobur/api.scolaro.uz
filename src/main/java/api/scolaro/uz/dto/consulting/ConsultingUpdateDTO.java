package api.scolaro.uz.dto.consulting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingUpdateDTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @NotNull(message = "Photo is required")
    private String photoId;
//    @NotBlank(message = "Owner name is required")
//    private String ownerName;
//    @NotBlank(message = "Owner surname is required")
//    private String ownerSurname;
    private String about;
    @NotNull(message = "Abbreviation is required")
    private String abbreviation;
}
