package api.scolaro.uz.entity.consulting;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.ConsultingTariffType;
import api.scolaro.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consulting_tariff")
public class ConsultingTariffEntity extends BaseEntity {
    @Column(name = "description_uz", columnDefinition = "text")
    private String descriptionUz;
    @Column(name = "description_ru", columnDefinition = "text")
    private String descriptionRu;
    @Column(name = "description_en", columnDefinition = "text")
    private String descriptionEn;
    @Column(name = "name", columnDefinition = "text")
    private String name;
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
    private ConsultingTariffType tariffType;
    @Column(name = "order_number")
    private Integer orderNumber;
}
