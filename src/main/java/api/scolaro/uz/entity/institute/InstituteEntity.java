package api.scolaro.uz.entity.institute;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseIdentityEntity;
import api.scolaro.uz.entity.place.CountryEntity;
import api.scolaro.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "institute")
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
    @Column(name = "about_uz", columnDefinition = "TEXT")
    private String aboutUz;
    @Column(name = "about_en", columnDefinition = "TEXT")
    private String aboutEn;
    @Column(name = "about_ru", columnDefinition = "TEXT")
    private String aboutRu;

    // abbreviation ?

    // Территория
    @Column(name = "territory_uz")
    private String territoryUz;
    @Column(name = "territory_ru")
    private String territoryEn;
    @Column(name = "territory_en")
    private String territoryRu;

    // Обучение
    @Column(name = "education_en")
    private String educationUz;
    @Column(name = "education_en")
    private String educationEn;
    @Column(name = "education_en")
    private String educationRu;

    // Программы  Shu universitetga bo'glangan programmalarni programmani ichida ko'rsatiladi. Bunda emas

    // Медия  photo+video(link)   InstituteAttachEntity - merge method yozish kerak
    // List<InstituteAttachEntity> ...

    // Направления -> InstituteDestinationEntity  merge method yozish kerak.

    // Контакты
    @Column(name = "phone_number_1")
    private String phoneNumber1;
    @Column(name = "phone_number_2")
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
