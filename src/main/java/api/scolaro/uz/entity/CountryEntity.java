package api.scolaro.uz.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "country")
public class CountryEntity {

    @Id
    private Long id;

    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;
    private LocalDateTime created_date=LocalDateTime.now();
    @Column(name = "visible")
    private Boolean visible;

}