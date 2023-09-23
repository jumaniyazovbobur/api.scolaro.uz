package api.scolaro.uz.entity.profile;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "consulting")
public class ConsultingEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "inn")
    private Integer inn;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
}
