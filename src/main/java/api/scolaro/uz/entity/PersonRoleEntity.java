package api.scolaro.uz.entity;

import api.dean.db.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person_role")
@Getter
@Setter
public class PersonRoleEntity extends BaseEntity {
    @Column(name = "person_id")
    private String personId; // client or profile
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
