package api.scolaro.uz.dto.profile;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    private String id;
    private String name;
    private String surname;
    private String phone;
    private LocalDateTime createdDate;
}
