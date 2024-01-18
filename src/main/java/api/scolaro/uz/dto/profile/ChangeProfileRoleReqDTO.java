package api.scolaro.uz.dto.profile;

import api.scolaro.uz.enums.RoleEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 'Mukhtarov Sarvarbek' on 17.01.2024
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeProfileRoleReqDTO {
    @NotEmpty(message = "Roles not be empty")
    private List<RoleEnum> roles;
}
