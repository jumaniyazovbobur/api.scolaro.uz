package api.scolaro.uz.entity;

import api.scolaro.uz.enums.StepType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consulting_step")
public class ConsultingStepEntity extends BaseEntity{
    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "step_type")
    private StepType stepType;
    @Column(name = "order_numbers")
    private String orderNumbers;
    @Column(name = "description")
    private String description;
    @Column(name = "consulting_id")
    private String consultingId;
    @ManyToOne
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;
}
