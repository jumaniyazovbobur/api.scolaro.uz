package api.scolaro.uz.entity.consulting;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "consulting")
public class ConsultingEntity extends BaseEntity {
    @Column(name = "name")
    private String name; // consulting firm name
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
    @Column(name = "photo_id")
    private String photoId;
    @Column(name = "compressed_photo_id")
    private String compressedPhotoId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private AttachEntity photo;
    @Column(name = "about", columnDefinition = "TEXT")
    private String about;
    @Column(name = "balance")
    private Long balance=0L;// tiyin 1sum = 100 tiyin
    @Column(name = "manager_id")
    private String managerId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", insertable = false, updatable = false)
    private ConsultingProfileEntity manager;

}
