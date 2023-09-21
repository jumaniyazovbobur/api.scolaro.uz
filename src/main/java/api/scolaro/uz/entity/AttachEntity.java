package api.scolaro.uz.entity;


import api.scolaro.uz.enums.AttachType;
import api.scolaro.uz.enums.FileType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.yaml.snakeyaml.comments.CommentType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "attach")
@Getter
@Setter
public class AttachEntity {

    @Id
    private String id;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "path")
    private String path;

    @Column(name = "extension")
    private String extension;

    @Column(name = "origen_name")
    private String origenName;

    @Column(name = "size")
    private Long size;

    @Column(nullable = true)
    private Boolean visible = true;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "deleted_date")
    private LocalDateTime deletedDate;

}
