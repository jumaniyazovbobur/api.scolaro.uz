package api.scolaro.uz.entity.place;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "country_flag")
public class CountryFlagEntity extends BaseEntity {

    @Column(name = "name_uz",length = 50,unique = true)
    private String nameUz;

    @Column(name = "name_ru",length = 50,unique = true)
    private String nameRu;

    @Column(name = "name_en",length = 50,unique = true)
    private String nameEn;

    @Column(name = "attach_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "order_number")
    private Integer orderNumber;

}
