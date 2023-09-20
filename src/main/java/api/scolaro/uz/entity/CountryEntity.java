package api.scolaro.uz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import uz.dachatop.entity.base.BaseLongIdEntity;

@Getter
@Setter
@Entity
@Table(name = "country")
public class CountryEntity extends BaseLongIdEntity {

    private String nameUz;
    private String nameRu;
    private String nameEn;

}