package api.scolaro.uz.entity.application;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.entity.consulting.ConsultingStepLevelEntity;
import api.scolaro.uz.enums.AttachType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Table(name = "app_application_level_attach")
@Entity
@Setter
@ToString
public class AppApplicationLevelAttachEntity extends BaseEntity {
    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "app_application_id", insertable = false, updatable = false)
//    private AppApplicationEntity appApplication;
//    @Column(name = "app_application_id")
//    private String appApplicationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_step_level_id", insertable = false, updatable = false)
    private ConsultingStepLevelEntity consultingStepLevel;
    @Column(name = "consulting_step_level_id")
    private String consultingStepLevelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;
    @Column(name = "attach_id")
    private String attachId;

    @Enumerated(EnumType.STRING)
    @Column(name = "attach_type")
    private AttachType attachType;
}
