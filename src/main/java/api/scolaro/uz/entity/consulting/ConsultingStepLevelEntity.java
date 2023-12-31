package api.scolaro.uz.entity.consulting;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.AttachType;
import api.scolaro.uz.enums.StepLevelStatus;
import api.scolaro.uz.enums.StepLevelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
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
    //    @Column(name = "step_level_type")
//    private StepLevelType stepLevelType;
    @Column(name = "order_number")
    private Integer orderNumber;
    @Column(name = "description_uz")
    private String descriptionUz;
    @Column(name = "description_ru")
    private String descriptionRu;
    @Column(name = "description_en")
    private String descriptionEn;
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

    @Column(name = "started_date")
    private LocalDateTime startedDate;
    @Column(name = "finished_date")
    private LocalDateTime finishedDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "step_level_status")
    private StepLevelStatus stepLevelStatus;
}
