package api.scolaro.uz.entity;


import api.scolaro.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
    @Column(name = "faculty_id")
    private Integer facultyId;
    @ManyToOne
    @JoinColumn(name = "faculty_id", insertable = false, updatable = false)
    private FacultyEntity faculty;
}
