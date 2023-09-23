package api.scolaro.uz.entity.sms;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.sms.SmsStatus;
import api.scolaro.uz.enums.sms.SmsType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "sms_history")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SmsHistoryEntity extends BaseEntity {
    @Column(name = "sms_code")
    private String smsCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SmsType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SmsStatus status;

    @Column(name = "sms_count")
    private Integer smsCount;

    @Column(name = "used_time")
    private LocalDateTime usedTime;

    @Column(name = "sms_text", columnDefinition = "text")
    private String smsText;
}
