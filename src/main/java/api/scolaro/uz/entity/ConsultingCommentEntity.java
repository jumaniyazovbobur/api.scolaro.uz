package api.scolaro.uz.entity;

import api.scolaro.uz.entity.consulting.ConsultingEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consulting_comment")
@Getter
@Setter
public class ConsultingCommentEntity extends BaseEntity {
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "consulting_id")
    private String consultingId;
    @ManyToOne
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;

    @Column(name = "student_id")
    private String studentId;
    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private ProfileEntity student;

}
