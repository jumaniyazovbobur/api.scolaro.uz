package api.scolaro.uz.dto.feeback;

import api.scolaro.uz.enums.FeedBackType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FeedbackFilterDTO {
    private String personId;
    private FeedBackType feedBackType;
    private LocalDate fromDate;
    private LocalDate toDate;
}
