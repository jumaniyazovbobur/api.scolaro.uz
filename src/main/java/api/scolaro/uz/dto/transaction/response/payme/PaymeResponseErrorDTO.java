package api.scolaro.uz.dto.transaction.response.payme;

import api.scolaro.uz.enums.jsonrpc.PaymeResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static api.scolaro.uz.enums.jsonrpc.PaymeResponseStatus.NOT_ENOUGH_PRIVILEGES;

/**
 * @author Admin on 08.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.dto.transaction.response.payme
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymeResponseErrorDTO {
    private Integer code;
    private String message;

    public static PaymeResponseErrorDTO NOT_ENOUGH_PRIVILEGES() {
        return new PaymeResponseErrorDTO(NOT_ENOUGH_PRIVILEGES.getCode(),"Invalid params");
    }
}
