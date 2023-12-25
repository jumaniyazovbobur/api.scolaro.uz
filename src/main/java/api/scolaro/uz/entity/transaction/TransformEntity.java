package api.scolaro.uz.entity.transaction;

import api.scolaro.uz.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transform")
@Getter
@Setter
public class TransformEntity extends BaseEntity {
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "consulting_id")
    private String consultingId;
    @Column(name = "application_id")
    private String applicationId;
    @Column(name = "step_level_id")
    private String consultingStepLevelId;
    @Column(name = "application_level_status_id")
    private String applicationLevelStatusId;
    @Column(name = "amount")
    private Long amount;
}
