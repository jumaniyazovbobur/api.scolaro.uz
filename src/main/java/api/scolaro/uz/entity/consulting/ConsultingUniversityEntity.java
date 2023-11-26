package api.scolaro.uz.entity.consulting;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.entity.UniversityEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consulting_university")
public class ConsultingUniversityEntity extends BaseEntity {

    @Column(name = "consulting_id")
    private String consultingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;

    @Column(name = "university_id")
    private Long universityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private UniversityEntity university;

   /* @Column(name = "tariff_id")
    private String tariffId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id", insertable = false, updatable = false)
    private ConsultingTariffEntity tariff;*/
}
