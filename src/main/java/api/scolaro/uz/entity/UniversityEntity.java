package api.scolaro.uz.entity;

import api.scolaro.uz.entity.place.CountryEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "university")
public class UniversityEntity extends BaseIdentityEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "web_site")
    private String webSite;
    @Column(name = "rating")
    private Long rating;
    @Column(name = "country_id")
    private Long countryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private CountryEntity country;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "abbreviation", columnDefinition = "TEXT")
    private String abbreviation;
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
}
