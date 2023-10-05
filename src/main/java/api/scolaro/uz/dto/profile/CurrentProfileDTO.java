package api.scolaro.uz.dto.profile;

import api.scolaro.uz.dto.attach.AttachResponseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class CurrentProfileDTO {
    private String name;
    private String surname;
    private String phone;
    private AttachResponseDTO photo;
}
