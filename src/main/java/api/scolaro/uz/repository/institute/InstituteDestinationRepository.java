package api.scolaro.uz.repository.institute;

import api.scolaro.uz.entity.institute.InstituteAttachEntity;
import api.scolaro.uz.entity.institute.InstituteDestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituteDestinationRepository extends JpaRepository<InstituteDestinationEntity, Long> {
    List<InstituteDestinationEntity> findByInstituteId(Long instituteId);

    void deleteByInstituteId(Long instituteId);
}
