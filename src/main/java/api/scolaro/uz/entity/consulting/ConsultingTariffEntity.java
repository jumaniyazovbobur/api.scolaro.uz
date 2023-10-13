package api.scolaro.uz.entity.consulting;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.ConsultingTarifType;
import api.scolaro.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consulting_tariff")
public class ConsultingTariffEntity extends BaseEntity {
    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "price")
    private Double price;
    @Column(name = "consulting_id")
    private String consultingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_id", insertable = false, updatable = false)
    private ConsultingEntity consulting;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GeneralStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "tariff_type")
    private ConsultingTarifType tariffType;
    @Column(name = "orders")
    private Integer order;
}
