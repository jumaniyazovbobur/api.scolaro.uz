package api.scolaro.uz.entity;

import api.scolaro.uz.enums.StepLevelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.beans.JavaBean;

@Getter
@Setter
@Entity
@Table(name = "consulting_step_level")
public class ConsultingStepLevelEntity extends BaseEntity{
    @Column(name = "nameUz")
    private String nameUz;
    @Column(name = "nameRu")
    private String nameRu;
    @Column(name = "nameEn")
    private String nameEn;
    @Column(name = "step_level_type")
    private StepLevelType stepLevelType;
    @Column(name = "order_numbers")
    private String orderNumbers;
    @Column(name = "description")
    private String description;
    @Column(name = "consulting_step_id")
    private String consultingStepId;
    @ManyToOne
    @JoinColumn(name = "consulting_step_id", insertable = false, updatable = false)
    private ConsultingStepEntity consultingStep;
    @Column(name = "prtId")
    private String prtId;
    @ManyToOne
    @JoinColumn(name = "prtId", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

}
