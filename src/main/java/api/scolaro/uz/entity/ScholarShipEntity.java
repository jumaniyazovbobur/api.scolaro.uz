package api.scolaro.uz.entity;

import api.scolaro.uz.enums.DegreeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "scholar_ship")
public class ScholarShipEntity extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "expired_date")
    private LocalDate expiredDate;
    @Column(name = "creator_id")
    private String  creatorId;


    @Enumerated(EnumType.STRING)
    @Column(name = "degree_type")
    private DegreeType degreeType;



    @Column(name = "photo_id")
    private String photoId;
    /*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "photo", insertable = false, updatable = false)
    private AttachEntity photo;*/



}
