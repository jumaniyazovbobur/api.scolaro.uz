package api.scolaro.uz.entity;

import api.dean.db.enums.AttachType;
import api.dean.db.enums.CommentType;
import api.dean.db.enums.FileType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "attach")
@Getter
@Setter
public class AttachEntity extends BaseEntity {
    @Column(name = "path")
    private String path;
    @Column(name = "extension")
    private String extension;
    @Column(name = "origen_name")
    private String origenName;
    @Column(name = "size")
    private Long size;
    @Enumerated(EnumType.STRING)
    @Column(name = "attach_type")
    private AttachType attachType;
    @Column(name = "given_date")
    private LocalDate givenDate;
    @Column(name = "comment_type")
    @Enumerated(EnumType.STRING)
    private CommentType commentType;
    @Column(name = "file_type")
    @Enumerated(EnumType.STRING)
    private FileType fileType;

}
