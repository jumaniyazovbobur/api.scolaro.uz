package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.enums.AppStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationFilterDTO {
    private String studentName;
    private String studentSurName;
    private String consultingName;
    private AppStatus status;
}
