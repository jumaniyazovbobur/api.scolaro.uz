package api.scolaro.uz.dto.transaction.request;

import api.scolaro.uz.enums.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author 'Mukhtarov Sarvarbek' on 18.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionFilterAsAdminDTO {

    private TransactionType type;
    private LocalDateTime toDate;
    private LocalDateTime fromDate;
}
