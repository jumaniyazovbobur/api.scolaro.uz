package api.scolaro.uz.entity.place;


import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseIdentityEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "destination") // Направления
public class DestinationEntity extends BaseIdentityEntity {

    @Column(name = "name_uz",length = 50,unique = true)
    private String nameUz;

    @Column(name = "name_ru",length = 50,unique = true)
    private String nameRu;

    @Column(name = "name_en",length = 50,unique = true)
    private String nameEn;

    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "show_in_main_page")
    private Boolean showInMainPage = Boolean.FALSE;

}