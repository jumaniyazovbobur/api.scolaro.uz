package api.scolaro.uz.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsDTO {
    private String phone;
    private String code;
}
