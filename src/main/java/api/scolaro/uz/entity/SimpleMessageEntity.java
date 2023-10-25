package api.scolaro.uz.entity;

import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "simple_message")
public class SimpleMessageEntity extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;
    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private AppApplicationEntity appApplication;
    @Column(name = "application_id")
    private String appApplicationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;
    @Column(name = "profile_id")
    private String studentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;
    @Column(name = "consulting_id")
    private String consultingId;
    @Column(name = "is_student_read")
    private Boolean isStudentRead=Boolean.FALSE;
    @Column(name = "is_consulting_read")
    private Boolean isConsultingRead=Boolean.FALSE;;
    @Column(name = "message", columnDefinition = "text")
    private String message;
    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
}
