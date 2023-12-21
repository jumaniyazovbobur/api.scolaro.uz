package api.scolaro.uz.dto.transaction.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 'Mukhtarov Sarvarbek' on 19.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WithdrawMoneyFromStudentDTO {
    private String studentId;
    private String consultingId;
    private Long amount; // in tiyin
    private String applicationId;
    private String consultingStepLevelId;
    private String applicationLevelStatusId;
}
