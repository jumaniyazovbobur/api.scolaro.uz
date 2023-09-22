package api.scolaro.uz.repository.profile;

import api.scolaro.uz.entity.profile.ConsultingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultingRepository extends JpaRepository<String, ConsultingEntity> {

}
