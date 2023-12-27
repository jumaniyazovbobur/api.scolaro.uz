package api.scolaro.uz.entity.consulting;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.entity.place.CountryEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.LanguageEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consulting_profile")
@Getter
@Setter
public class ConsultingProfileEntity extends BaseEntity {
    @Column(name = "fire_base_id")
    private String fireBaseId;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
    @Column(name = "photo_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;
    @Column(name = "temp_phone")
    private String tempPhone;
    @Column(name = "temp_sms_code")
    private String smsCode;
    @Column(name = "country_id")
    private Long countryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private CountryEntity country;
    @Column(name = "adress", columnDefinition = "text")
    private String address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;
    @Column(name = "consulting_id")
    private String consultingId;

    @Column(name = "is_online")
    private Boolean isOnline = false;
    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private LanguageEnum lang = LanguageEnum.uz;
}
