package api.scolaro.uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "web_student")
public class WebStudentEntity extends BaseEntity  {
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "about_uz", columnDefinition = "TEXT")
    private String aboutUz;
    @Column(name = "about_en", columnDefinition = "TEXT")
    private String aboutEn;
    @Column(name = "about_ru", columnDefinition = "TEXT")
    private String aboutRu;

    @Column(name = "photo_id")
    private String photoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;
    @Column(name = "order_number")
    private Integer orderNumber;
}
