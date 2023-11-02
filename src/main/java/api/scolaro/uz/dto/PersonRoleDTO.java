package api.scolaro.uz.dto;

import api.scolaro.uz.enums.RoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PersonRoleDTO {
    private String id;
    private List<RoleEnum> role;

    public PersonRoleDTO(String id, List<RoleEnum> role) {
        this.id = id;
        this.role = role;
    }
}
