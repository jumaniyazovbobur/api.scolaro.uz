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
public class PerformTransactionResult {
    private String transaction;
    private Long perform_time;
    private int state;

    public PerformTransactionResult(String id, long perform_time, int state) {
        this.transaction=id;
        this.perform_time=perform_time;
        this.state=state;
    }
}
