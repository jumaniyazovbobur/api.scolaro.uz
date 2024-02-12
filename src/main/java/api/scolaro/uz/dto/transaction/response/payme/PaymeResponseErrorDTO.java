package api.scolaro.uz.dto.transaction.response.payme;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymeResponseErrorDTO {
    private Integer code;
    private String message;
    private String data;

    public PaymeResponseErrorDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PaymeResponseErrorDTO NOT_ENOUGH_PRIVILEGES() {
        return new PaymeResponseErrorDTO(NOT_ENOUGH_PRIVILEGES.getCode(), "Invalid params");
    }

    public static PaymeResponseErrorDTO ErrorsOfIncorrectDataEntry() {
        return new PaymeResponseErrorDTO(-31099, "Invalid params");
    }
}
