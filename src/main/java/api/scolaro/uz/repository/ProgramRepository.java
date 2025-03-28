package api.scolaro.uz.repository;

import api.scolaro.uz.entity.ProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {
    Optional<ProgramEntity> findByIdAndVisibleTrue(Long id);

    @Query("from ProgramEntity pe where pe.university.countryId=?1")
    List<ProgramEntity> findAllByVisibleTrueOrderByNameAsc();

}
