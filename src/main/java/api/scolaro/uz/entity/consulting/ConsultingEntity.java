package api.scolaro.uz.entity.consulting;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseEntity;
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
    private String name; // consulting firm name
    @Column(name = "owner_name")
    private String ownerName;
    @Column(name = "owner_surname")
    private String ownerSurname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "address",columnDefinition = "TEXT")
    private String address;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
    @Column(name = "photo_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;
    @Column(name = "about",columnDefinition = "TEXT")
    private String about;
    @Column(name = "temp_phone")
    private String tempPhone;
    @Column(name = "temp_sms_code")
    private String smsCode;
}
