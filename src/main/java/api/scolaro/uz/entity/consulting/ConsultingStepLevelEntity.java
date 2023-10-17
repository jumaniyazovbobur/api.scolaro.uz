package api.scolaro.uz.entity.consulting;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.StepLevelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consulting_step_level")
public class ConsultingStepLevelEntity extends BaseEntity {
    @Column(name = "nameUz")
    private String nameUz;
    @Column(name = "nameRu")
    private String nameRu;
    @Column(name = "nameEn")
    private String nameEn;
    @Column(name = "step_level_type")
    private StepLevelType stepLevelType;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "description")
    private String description;
    @Column(name = "consulting_step_id")
    private String consultingStepId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_step_id", insertable = false, updatable = false)
    private ConsultingStepEntity consultingStep;
    @Column(name = "consulting_id")
    private String consultingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;
}