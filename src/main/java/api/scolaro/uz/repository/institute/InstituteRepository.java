package api.scolaro.uz.repository.institute;

import api.scolaro.uz.entity.institute.InstituteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstituteRepository extends JpaRepository<InstituteEntity, Long> {


    Optional<InstituteEntity> findByIdAndVisibleTrue(Long id);
}
