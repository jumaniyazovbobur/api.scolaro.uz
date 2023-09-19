package api.scolaro.uz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(nullable = true)
    private Boolean visible = true;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "update_id")
    private String updateId;

    @Column(name = "delete_id")
    private String deleteId;
}
