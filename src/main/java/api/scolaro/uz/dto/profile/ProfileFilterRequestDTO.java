package api.scolaro.uz.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileFilterRequestDTO {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private Integer facultyId;
}
