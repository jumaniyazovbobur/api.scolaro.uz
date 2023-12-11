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
public class CancelTransactionResult {
    private String transaction;
    private Long cancel_time;
    private Integer state;

    public CancelTransactionResult(String id, long cancel_time, int state) {
        this.transaction = id;
        this.cancel_time = cancel_time;
        this.state = state;
    }
}
