package api.scolaro.uz.dto.consulting;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingCreateDTO {
    @NotBlank(message = "Name is required")
    private String name; // text
    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message = "Address is required")
    private String address; // text
    @NotBlank(message = "Owner name is required")
    private String ownerName;
    @NotBlank(message = "Owner surname is required")
    private String ownerSurname;
    @NotNull(message = "Photo is required")
    private String photoId;
    private String about;
    private String fireBaseId;

}
