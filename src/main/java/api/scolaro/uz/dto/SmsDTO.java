package api.scolaro.uz.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsDTO {
    private String phone;
    private String code;
}
