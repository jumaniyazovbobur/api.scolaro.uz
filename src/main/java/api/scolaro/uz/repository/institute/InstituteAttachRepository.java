package api.scolaro.uz.repository.institute;

import api.scolaro.uz.entity.institute.InstituteAttachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituteAttachRepository extends JpaRepository<InstituteAttachEntity,Long> {

    List<InstituteAttachEntity> findByInstituteId(Long instituteId);

    void deleteByInstituteId(Long instituteId);
}
