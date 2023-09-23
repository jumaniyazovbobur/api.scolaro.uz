package api.scolaro.uz.dto.profile;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ConsultingFilterDTO{
    private String name;
    private String address;
    private LocalDate fromCreatedDate;
    private LocalDate toCreatedDate;
}
