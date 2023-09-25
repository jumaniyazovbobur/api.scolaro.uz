package api.scolaro.uz.dto.consulting;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultingRegDTO {

    @NotBlank(message = "Name is required")
    private String name; // text
    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password min 5 character")
    private String password;
    @NotBlank(message = "Address is required")
    private String address; // text

}
