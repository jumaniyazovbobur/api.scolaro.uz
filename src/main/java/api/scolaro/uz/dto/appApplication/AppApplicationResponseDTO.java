package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.enums.AppStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationResponseDTO {

        private String id;
        private String studentId;
        private String consultingId;
        private Long universityId;
        private AppStatus status;
        private LocalDateTime createdDate;

}
