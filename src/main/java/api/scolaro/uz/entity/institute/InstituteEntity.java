package api.scolaro.uz.entity.institute;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseIdentityEntity;
import api.scolaro.uz.entity.place.CountryEntity;
import api.scolaro.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
//@Entity
//@Table(name = "institute")
public class InstituteEntity extends BaseIdentityEntity {
    private String name;

    @Column(name = "country_id")
    private Long countryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private CountryEntity country;
    private String cityName;

    @Column(name = "rating")
    private Long rating;

    // Об университете
    private String aboutUz;
    private String aboutEn;
    private String aboutRu;

    // abbreviation ?

    // Территория
    private String territoryUz;
    private String territoryEn;
    private String territoryRu;

    // Обучение
    private String educationUz;
    private String educationEn;
    private String educationRu;

    // Программы  Shu universitetga bo'glangan programmalarni programmani ichida ko'rsatiladi. Bunda emas

    // Медия  photo+video(link)   InstituteAttachEntity - merge method yozish kerak
    // List<InstituteAttachEntity> ...

    // Направления -> InstituteDestinationEntity  merge method yozish kerak.

    // Контакты
    private String phoneNumber1;
    private String phoneNumber2;
    @Column(name = "web_site")
    private String webSite;
    @Column(name = "instagram_url")
    private String instagramUrl;
    @Column(name = "facebook_url")
    private String facebookUrl;
    @Column(name = "email")
    private String email;

    @Column(name = "photo_id")
    private String photoId;
    @Column(name = "compressed_photo_id")
    private String compressedPhotoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;

    @Column(name = "logo_id")
    private String logoId;
    @Column(name = "compressed_logo_id")
    private String compressedLogoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_id", insertable = false, updatable = false)
    private AttachEntity logo;

    @Column(name = "master_degree")
    private Boolean masterDegree;

    @Column(name = "doctoral_degree")
    private Boolean doctoralDegree;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;


}
