package api.scolaro.uz.entity.sms;



import api.scolaro.uz.enums.sms.SmsStatus;
import api.scolaro.uz.enums.sms.SmsType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "app_sms")
public class SmsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SmsStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SmsType type;

    @Column(name = "sms_code")
    private String smsCode;

    @Column(name = "created_date")
    @CreationTimestamp
    protected LocalDateTime createdDate = LocalDateTime.now();
}
