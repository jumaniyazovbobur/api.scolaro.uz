package api.scolaro.uz.entity;


import api.scolaro.uz.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person_role")
@Getter
@Setter
public class PersonRoleEntity extends BaseEntity {
    @Column(name = "person_id")
    private String personId; // user or profile

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;
}
