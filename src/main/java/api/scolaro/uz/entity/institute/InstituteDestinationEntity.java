package api.scolaro.uz.entity.institute;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.entity.place.DestinationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "institute_destination")  //  Направления Университета - universitet yo'nalishlari
public class InstituteDestinationEntity extends BaseEntity {
    @Column(name = "destination_id")
    private Long destinationId;
    @ManyToOne
    @JoinColumn(name = "destination_id", insertable = false, updatable = false)
    private DestinationEntity destination;

    @Column(name = "institute_id")
    private Long instituteId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", insertable = false, updatable = false)
    private InstituteEntity institute;
}
