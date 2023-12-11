package api.scolaro.uz.dto.transaction.response.payme;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Admin on 08.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.dto.transaction.response.payme
 * @contact @sarvargo
 */
@Getter
@Setter
public class CheckPerformTransactionResult {
    private Boolean allow;

    public CheckPerformTransactionResult(boolean b) {
        this.allow = b;
    }
}
