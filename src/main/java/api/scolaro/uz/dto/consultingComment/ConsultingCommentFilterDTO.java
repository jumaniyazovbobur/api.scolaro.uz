package api.scolaro.uz.dto.consultingComment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ConsultingCommentFilterDTO {
    private String studentId;
    private String consultingId;
    private LocalDate fromCreatedDate;
    private LocalDate toCreatedDate;
}
