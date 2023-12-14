package api.scolaro.uz.dto.transaction;

import api.scolaro.uz.entity.transaction.TransactionsEntity;
import api.scolaro.uz.enums.transaction.TransactionStatus;
import api.scolaro.uz.enums.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 'Mukhtarov Sarvarbek' on 05.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@AllArgsConstructor
@Data
public class TransactionResponseDTO {
    private String id;
    private TransactionStatus status;
    private String profileId;
    private Long amount;
    private TransactionType transactionType;

    public static TransactionResponseDTO toDTO(TransactionsEntity entity) {
        return new TransactionResponseDTO(entity.getId(), entity.getStatus(), entity.getProfileId(), entity.getAmount(), entity.getTransactionType());
    }
}
