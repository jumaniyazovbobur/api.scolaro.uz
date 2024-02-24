package api.scolaro.uz.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResetProfileDTO {
    @NotNull(message = "Phone required")
    @Size(min = 9, max = 13, message = "Phone not valid")
    private String phone;
}
