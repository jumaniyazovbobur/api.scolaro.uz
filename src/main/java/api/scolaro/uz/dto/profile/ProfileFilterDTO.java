package api.scolaro.uz.dto.profile;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDTO {

    private String name;
    private String surname;
    private LocalDate fromCreatedDate;
    private LocalDate toCreatedDate;
}
