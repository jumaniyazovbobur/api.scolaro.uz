package api.scolaro.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyValueDTO {
    private String key;
    private String value;

    public KeyValueDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValueDTO() {
    }
}
