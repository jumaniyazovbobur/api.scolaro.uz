package api.scolaro.uz.service.transaction;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;

import java.util.Map;

/**
 * @author 'Mukhtarov Sarvarbek' on 04.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
public interface TransactionService {

    ApiResponse<TransactionResponseDTO> createTransactionForFillBalance(String currentUserId, Long amount);

    Map<String, Object> callBackPayme(PaymeCallBackRequestDTO body);
}
