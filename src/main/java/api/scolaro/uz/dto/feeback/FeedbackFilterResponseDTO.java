package api.scolaro.uz.dto.feeback;

import api.scolaro.uz.enums.FeedBackType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackFilterResponseDTO {
    private String id;
    private String content;
    private FeedBackType feedBackType;
}
