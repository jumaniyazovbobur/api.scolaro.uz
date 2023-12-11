package api.scolaro.uz.enums.transaction;

import lombok.Getter;

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
}
