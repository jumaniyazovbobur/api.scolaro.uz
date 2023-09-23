package api.scolaro.uz.dto.consulting;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ConsultingDTO {
    private String id;
    private String name;
    private Integer inn;
    private String phone;
    private String address;
    private LocalDateTime createdDate;

}
