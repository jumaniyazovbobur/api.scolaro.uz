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
public class CheckTransactionResult {
    private String transaction;
    private Long create_time;
    private Long perform_time;
    private Long cancel_time;
    private int state;
    private Integer reason;

    public CheckTransactionResult(Long create_time, Long perform_time, Long cancel_time, String id,
                                  int state, Integer reason) {
        this.create_time = create_time;
        this.transaction = id;
        this.cancel_time = cancel_time;
        this.perform_time = perform_time;
        this.reason = reason;
        this.state = state;
    }
}
