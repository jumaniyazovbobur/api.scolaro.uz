package api.scolaro.uz.dto.profile;

import api.scolaro.uz.dto.attach.AttachDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDTO {

    private String id;
    private String name;
    private String surname;
    private String phone;
    private String password;
    private AttachDTO photo;
    private LocalDateTime createdDate;
}
