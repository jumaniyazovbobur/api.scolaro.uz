package api.scolaro.uz.repository;

import api.scolaro.uz.entity.ProgramRequirementEntity;
import api.scolaro.uz.entity.place.DestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProgramRequirementRepository extends JpaRepository<ProgramRequirementEntity,Long> {

    void deleteAllByProgramId(Long programId);
    List<ProgramRequirementEntity> findAllByProgramId(Long id);
}
