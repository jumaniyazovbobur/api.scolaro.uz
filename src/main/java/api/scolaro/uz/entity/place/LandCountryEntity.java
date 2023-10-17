package api.scolaro.uz.entity.place;

import api.scolaro.uz.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "land_country")
public class LandCountryEntity extends BaseEntity {
    @Column(name = "land_id")
    private Long landId;
    @Column(name ="country_id" )
    private Long countryId;
}
