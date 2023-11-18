package api.scolaro.uz.entity.scholarShip;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.enums.UniversityDegreeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scholar_ship_degree_types")
@Getter
@Setter
public class ScholarShipDegreeTypeEntity extends BaseEntity {
    @Column(name = "scholar_ship_id")
    private String scholarShipId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scholar_ship_id", insertable = false, updatable = false)
    private ScholarShipEntity scholarShip;

    @Column(name = "degree_type")
    @Enumerated(EnumType.STRING)
    private UniversityDegreeType degreeType;

}
