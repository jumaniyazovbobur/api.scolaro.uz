package api.scolaro.uz.entity.institute;

import api.scolaro.uz.entity.AttachEntity;
import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.InstituteAttachType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "institute_attach")  //  Направления Университета - universitet yo'nalishlari
public class InstituteAttachEntity extends BaseEntity {
    @Column(name = "institute_id")
    private Long instituteId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", insertable = false, updatable = false)
    private InstituteEntity institute;

    // agar instituteAttachType.ATTACH bo'lsa bunda qiymat bo'ladi
    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Enumerated(EnumType.STRING)
    @Column(name = "institute_attach_type")
    private InstituteAttachType instituteAttachtype;

    // agar instituteAttachType.VIDEO bo'lsa bunda qiymat bo'ladi
    @Column(name = "video_link")
    private String videoLink;

}
