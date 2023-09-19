package api.scolaro.uz.entity.sms;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "sms_token")
public class SmsTokenEntity {

    @Id
    private String email;
    @Column(columnDefinition = "TEXT")
    private String token;
    //    @Column()
    private LocalDate createdDate;
}
