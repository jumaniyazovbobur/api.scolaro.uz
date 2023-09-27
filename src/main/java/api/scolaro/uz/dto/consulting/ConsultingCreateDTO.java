package api.scolaro.uz.dto.consulting;


import jakarta.validation.constraints.NotBlank;
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
    private String photoId;
    private String about;

}
