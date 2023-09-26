package api.scolaro.uz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "university")
public class UniversityEntity {

    @Id
    private Long id;
    private String name;
    private Long countryId;
    private String webSite;
    private String Description; // text
    private String photoId;

}
