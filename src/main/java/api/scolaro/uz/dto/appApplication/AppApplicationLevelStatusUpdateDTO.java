package api.scolaro.uz.dto.appApplication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppApplicationLevelStatusUpdateDTO {
    private LocalDate deadline;
    private String description;
}
