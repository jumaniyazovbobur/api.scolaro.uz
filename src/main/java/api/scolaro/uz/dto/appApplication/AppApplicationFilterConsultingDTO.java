package api.scolaro.uz.dto.appApplication;

import api.scolaro.uz.enums.AppStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationFilterConsultingDTO {
    private String name;
    private AppStatus status;
}
