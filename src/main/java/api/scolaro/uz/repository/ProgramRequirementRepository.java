package api.scolaro.uz.repository;

import api.scolaro.uz.entity.ProgramRequirementEntity;
import api.scolaro.uz.entity.place.DestinationEntity;
import api.scolaro.uz.enums.ProgramRequirementType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProgramRequirementRepository extends JpaRepository<ProgramRequirementEntity, Long> {

    void deleteAllByProgramId(Long programId);

    List<ProgramRequirementEntity> findAllByProgramId(Long id);

    @Query("select  type From ProgramRequirementEntity where programId = ?1")
    List<ProgramRequirementType> findRequirementTypeListByProgramId(Long programId);

    @Transactional
    @Modifying
    @Query("delete From ProgramRequirementEntity where programId = ?1 and type =?2")
    void deleteByProgramIdAndRequirementType(Long programId, ProgramRequirementType type);
}
