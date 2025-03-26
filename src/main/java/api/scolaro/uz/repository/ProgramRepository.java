package api.scolaro.uz.repository;

import api.scolaro.uz.entity.ProgramEntity;
import api.scolaro.uz.entity.place.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {
    Optional<ProgramEntity> findByIdAndVisibleTrue(Long id);
    List<ProgramEntity> findAllByVisibleTrue();
}
