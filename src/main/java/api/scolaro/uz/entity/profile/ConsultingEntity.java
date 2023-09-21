package api.scolaro.uz.entity.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consulting")
public class ConsultingEntity {

    @Id
    private Long id;
    private String name;
    private Integer INN;

}
