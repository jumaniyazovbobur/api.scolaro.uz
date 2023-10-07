package api.scolaro.uz.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseIdentityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;
    @Column(name = "created_id")
    private String createdId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_id", insertable = false, updatable = false)
    private ProfileEntity createdProfile;
    @Column(name = "deleted_id")
    private String deletedId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_id", insertable = false, updatable = false)
    private ProfileEntity deletedProfile;

    public BaseIdentityEntity(Long id) {
        this.id = id;
    }
}
