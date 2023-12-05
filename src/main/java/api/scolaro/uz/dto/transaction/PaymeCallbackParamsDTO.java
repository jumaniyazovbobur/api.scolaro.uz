package api.scolaro.uz.dto.transaction;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

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
public class PaymeCallbackParamsDTO {
    private int amount;
    private Map<String, Object> account = new HashMap<>();

    @JsonAnySetter
    public void setAccountProperty(String key, Object value) {
        account.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAccountProperties() {
        return account;
    }
}
