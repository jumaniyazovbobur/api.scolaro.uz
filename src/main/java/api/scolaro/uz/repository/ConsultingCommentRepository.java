package api.scolaro.uz.repository;

import api.scolaro.uz.entity.ConsultingCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultingCommentRepository extends JpaRepository<ConsultingCommentEntity, String> {
    List<ConsultingCommentEntity> getByConsultingId(String id);
}
