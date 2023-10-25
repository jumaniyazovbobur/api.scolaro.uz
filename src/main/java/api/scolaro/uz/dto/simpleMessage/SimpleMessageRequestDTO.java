package api.scolaro.uz.dto.simpleMessage;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleMessageRequestDTO {
    @NotNull(message = "applicationId is required")
    private String applicationId;
    @NotNull(message = "message is required")
    private String message;
    @NotNull(message = "attachId is required")
    private String attachId;
}
