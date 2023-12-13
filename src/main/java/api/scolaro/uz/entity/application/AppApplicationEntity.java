package api.scolaro.uz.entity.application;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.*;
import api.scolaro.uz.enums.AppStatus;
import api.scolaro.uz.enums.ApplicationStepLevelStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Table(name = "app_application")
@Entity
@Setter
@ToString
public class AppApplicationEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private ProfileEntity student;
    @Column(name = "student_id")
    private String studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;
    @Column(name = "consulting_id")
    private String consultingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_profile_id", insertable = false, updatable = false)
    private ConsultingProfileEntity consultingProfile;
    @Column(name = "consulting_profile_id")
    private String consultingProfileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private UniversityEntity university;
    @Column(name = "university_id")
    private Long universityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppStatus status;

    @Column(name = "started_date")
    private LocalDateTime startedDate;

    @Column(name = "finished_date")
    private LocalDateTime finishedDate;

    @Column(name = "canceled_date")
    private LocalDateTime canceledDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_tariff_id", insertable = false, updatable = false)
    private ConsultingTariffEntity consultingTariff;
    @Column(name = "consulting_tariff_id")
    private String consultingTariffId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_step_id", insertable = false, updatable = false)
    private ConsultingStepEntity consultingStep;
    @Column(name = "consulting_step_id")
    private String consultingStepId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_step_level_id", insertable = false, updatable = false)
    private ConsultingStepLevelEntity consultingStepLevel;
    @Column(name = "consulting_step_level_id")
    private String consultingStepLevelId; // application current step level id

    /*@Enumerated(EnumType.STRING)
    @Column(name = "application_step_level_status")
    private ApplicationStepLevelStatus applicationStepLevelStatus;*/

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_step_level_status_id", insertable = false, updatable = false)
    private AppApplicationLevelStatusEntity AppApplicationLevelStatus;
    @Column(name = "consulting_step_level_status_id")
    private String consultingStepLevelStatusId; // application current step level status id

    @Column(name = "application_number")
    private Long applicationNumber;
}