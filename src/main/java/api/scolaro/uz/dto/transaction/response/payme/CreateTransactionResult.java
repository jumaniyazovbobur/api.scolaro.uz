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
public class CreateTransactionResult {
    private String transaction;
    private Long create_time;
    private int state;

    public CreateTransactionResult(String transactionId, long createTime, int state) {
        this.transaction = transactionId;
        this.create_time = createTime;
        this.state = state;
    }
}
