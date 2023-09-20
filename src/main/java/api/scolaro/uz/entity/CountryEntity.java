package api.scolaro.uz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "country")
public class CountryEntity {

    @Id
    private Long id;
    private String nameUz;
    private String nameRu;
    private String nameEn;
    private Boolean visible;

}