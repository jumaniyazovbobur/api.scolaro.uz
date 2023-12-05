package api.scolaro.uz.enums.jsonrpc;

import lombok.Getter;

/**
 * @author 'Mukhtarov Sarvarbek' on 05.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Getter
public enum PaymeResponseStatus {
    CANCEL_TRANSACTION("-31008"),
    INVALID_AMOUNT("-31001"),
    INVALID_PARAMS("-31050");

    private String code;

    PaymeResponseStatus(String code) {
        this.code = code;
    }
}
