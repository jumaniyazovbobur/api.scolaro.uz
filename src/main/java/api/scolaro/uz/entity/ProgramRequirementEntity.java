package api.scolaro.uz.entity;


import api.scolaro.uz.enums.ProgramRequirementType;
import api.scolaro.uz.enums.ProgramType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "program_requirement")
public class ProgramRequirementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "program_id")
    private Long programId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", insertable = false, updatable = false)
    private ProgramEntity program;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProgramRequirementType type;

}
