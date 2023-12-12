package api.scolaro.uz.repository;

import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.consulting.ConsultingProfileEntity;
import org.springframework.data.repository.CrudRepository;

public interface ConsultingProfileRepository extends CrudRepository<ConsultingProfileEntity, String> {
}
