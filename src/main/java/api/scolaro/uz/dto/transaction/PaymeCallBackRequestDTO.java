package api.scolaro.uz.dto.transaction;

import lombok.*;


/**
 * @author 'Mukhtarov Sarvarbek' on 05.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymeCallBackRequestDTO {
    private String method;
    private PaymeCallbackParamsDTO params;
}
