package api.scolaro.uz.entity.place;

import api.scolaro.uz.entity.BaseIdentityEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "continent_country")
public class ContinentCountryEntity extends BaseIdentityEntity {
    @Column(name = "continent_id")
    private Long continentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "continent_id", insertable = false, updatable = false)
    private ContinentEntity continent;
    @Column(name = "country_id")
    private Long countryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private CountryEntity country;
    @Column(name = "order_number")
    private Integer orderNumber;

}
