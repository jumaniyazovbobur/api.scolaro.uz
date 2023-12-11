package api.scolaro.uz.dto.transaction.response.payme;

import api.scolaro.uz.dto.transaction.TransactionResForPayme;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Admin on 08.12.2023
 * @project api.scolaro.uz
 * @package api.scolaro.uz.dto.transaction.response.payme
 * @contact @sarvargo
 */
@Getter
@Setter
public class GetStatementResult {
    List<TransactionResForPayme> transactions;

    public GetStatementResult(List<TransactionResForPayme> listForResponse) {
        transactions = listForResponse;
    }
}
