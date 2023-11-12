package api.scolaro.uz.entity;

import api.scolaro.uz.enums.UniversityDegreeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "university_degree_type")
public class UniversityDegreeTypeEntity extends BaseEntity {

    @Column(name = "university_id")
    private Long universityId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", insertable = false, updatable = false)
    private UniversityEntity universityEntity;

    @Column(name = "degree_type")
    @Enumerated(EnumType.STRING)
    private UniversityDegreeType degreeType;
}
