package api.scolaro.uz.entity;

import api.scolaro.uz.entity.consulting.ConsultingStepLevelEntity;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import api.scolaro.uz.enums.StepLevelStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Table(name = "app_application_level_status")
@Entity
@Setter
@ToString
public class AppApplicationLevelStatusEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_application_id", insertable = false, updatable = false)
    private AppApplicationEntity appApplication;
    @Column(name = "app_application_id")
    private String appApplicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_stepLevel_id", insertable = false, updatable = false)
    private ConsultingStepLevelEntity consultingStepLevel;
    @Column(name = "consulting_stepLevel_id")
    private String consultingStepLevelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_step_level_status")
    private ApplicationStepLevelStatus applicationStepLevelStatus;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "description")
    private String description;
}
