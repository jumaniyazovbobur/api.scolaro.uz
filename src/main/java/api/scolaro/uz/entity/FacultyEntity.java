package api.scolaro.uz.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "faculty")
@Getter
@Setter
public class FacultyEntity extends BaseEntity {
    @Column(name = "name_uz")
    private String nameUz;
    @Column(name = "name_ru")
    private String nameRu;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "parent_id")
    private String parentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private FacultyEntity parent;
    @Column(name = "order_number")
    private Integer orderNumber;
}

