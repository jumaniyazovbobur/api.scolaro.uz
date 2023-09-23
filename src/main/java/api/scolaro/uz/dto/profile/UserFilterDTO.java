package api.scolaro.uz.dto.profile;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserFilterDTO {

    private String name;
    private String surname;
    private LocalDate fromCreatedDate;
    private LocalDate toCreatedDate;
}
