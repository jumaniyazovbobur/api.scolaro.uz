package api.scolaro.uz.dto.feeback;

import api.scolaro.uz.enums.FeedBackType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedbackDTO {
    @NotBlank
    private String content;
}
