package api.scolaro.uz.dto.transaction.response;

import api.scolaro.uz.enums.transaction.TransactionStatus;
import api.scolaro.uz.enums.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 'Mukhtarov Sarvarbek' on 19.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionResponseAsStudentDTO {
    private String id;
    private Long amount; // in tiyinax
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime createdDate;
    private Long applicationNumber;
}
