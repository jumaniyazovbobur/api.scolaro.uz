package api.scolaro.uz.enums.jsonrpc;

import lombok.Getter;

/**
 * @author 'Mukhtarov Sarvarbek' on 05.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Getter
public enum PaymeResponseStatus {
    INVALID_STATE(-31008),
    NOT_ENOUGH_PRIVILEGES(-32504),
    INVALID_AMOUNT(-31001),
    TRANSACTION_NOT_FOUND(-31003),
    COULD_NOT_CANCEL(-31007),
    INVALID_PARAMS(-31050);

    private int code;

    PaymeResponseStatus(int code) {
        this.code = code;
    }
}
