package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.entity.ConsultingCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultingCommentRepository extends JpaRepository<ConsultingCommentEntity, String> {
    Page<ConsultingCommentEntity> getByConsultingIdAndVisibleTrue(String id, Pageable pageable); //TODO  visible = true order by created_date desc
    @Modifying
    @Transactional
    @Query("update ConsultingCommentEntity  set visible = false  where id = ?1")
    void deleteConsultingCommentId(String consultingCommentId);
}
