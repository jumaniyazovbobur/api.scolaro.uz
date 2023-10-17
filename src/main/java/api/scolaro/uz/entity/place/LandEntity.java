package api.scolaro.uz.entity.place;

import api.scolaro.uz.entity.BaseIdentityEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "land")
public class LandEntity extends BaseIdentityEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "order_by")
    private Integer orderBy;
}
