package api.scolaro.uz.entity.scholarShip;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingStepLevelEntity;
import api.scolaro.uz.enums.DegreeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "creator_id")
    private String creatorId;
    @Column(name = "university_id")
    private Long universityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private UniversityEntity university;
    @Column(name = "price")
    private Integer price;
    @Column(name = "photo_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;
}
