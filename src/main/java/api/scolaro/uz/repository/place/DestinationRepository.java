package api.scolaro.uz.repository.place;

import api.scolaro.uz.entity.place.DestinationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DestinationRepository extends JpaRepository<DestinationEntity, Long> {

    List<DestinationEntity> findAllByVisibleTrueOrderByOrderNumber();

    Page<DestinationEntity> findAllByVisibleTrueOrderByOrderNumber(Pageable pageable);

    Optional<DestinationEntity> findByIdAndVisibleTrue(Long id);


}
