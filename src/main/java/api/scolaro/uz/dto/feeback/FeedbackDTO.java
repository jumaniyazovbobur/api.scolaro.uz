package api.scolaro.uz.dto.feeback;

import api.scolaro.uz.enums.FeedBackType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedbackDTO {
    private String content;
    private FeedBackType feedbackType;
}
