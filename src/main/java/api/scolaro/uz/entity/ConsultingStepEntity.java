package api.scolaro.uz.entity;

import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.StepType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "consulting_step")
public class ConsultingStepEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "step_type")
    private StepType stepType;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "description")
    private String description;
    @Column(name = "consulting_id")
    private String consultingId;
    @ManyToOne
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;
    @OneToMany(mappedBy = "consultingStep")
    private List<ConsultingStepLevelEntity> levelList;
}
