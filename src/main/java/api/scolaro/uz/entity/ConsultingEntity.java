package api.scolaro.uz.entity;

import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "consulting")
public class ConsultingEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    //    @Column(name = "inn")
//    private Integer inn;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
    //    @Enumerated(EnumType.STRING)
//    @Column(name = "role")
//    private RoleEnum role;
    @Column(name = "temp_phone")
    private String tempPhone;
    @Column(name = "sms_code")
    private String smsCode;

    // TODO phone
    // TODO about (text)


}
