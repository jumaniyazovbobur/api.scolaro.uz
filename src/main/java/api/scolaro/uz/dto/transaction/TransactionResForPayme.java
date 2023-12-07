package api.scolaro.uz.dto.transaction;

import api.scolaro.uz.entity.transaction.TransactionsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;


/**
 * @author 'Mukhtarov Sarvarbek' on 07.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResForPayme {
    @JsonProperty("id")
    String paymeTransactionId; // payme
    Long time; //createdDate
    Long amount;
    @JsonProperty("perform_time")
    Long performTime;
    @JsonProperty("cancel_time")
    Long cancelTime;
    @JsonProperty("transaction")
    String id;
    int state;
    String reason;

    public static TransactionResForPayme toDTO(TransactionsEntity entity) {
        Long createdDateMilli = entity.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long performTimeMilli = entity.getPerformTime() == null ? 0L : entity.getPerformTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long cancelTimeMilli = entity.getCancelTime() == null ? 0L : entity.getCancelTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return new TransactionResForPayme(entity.getPaymeTransactionsId(), createdDateMilli, entity.getAmount(), performTimeMilli, cancelTimeMilli, entity.getId(), entity.getState().getValue(), entity.getReason());
    }
}
