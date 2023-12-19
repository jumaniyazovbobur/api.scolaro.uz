package api.scolaro.uz.enums.transaction;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author 'Mukhtarov Sarvarbek' on 06.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Getter
public enum TransactionState {
    STATE_NEW(0),
    STATE_IN_PROGRESS(1),
    STATE_DONE(2),
    STATE_CANCELED(-1),
    STATE_POST_CANCELED(-2);
    int value;

    TransactionState(int value) {
        this.value = value;
    }

    public static TransactionState parse(Integer integerValue) {
        return Arrays
                .stream(TransactionState.values())
                .filter(item -> item.value == integerValue)
                .findAny()
                .orElse(null);
    }
}
