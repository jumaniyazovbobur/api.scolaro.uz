package api.scolaro.uz.repository.feedback;

import api.scolaro.uz.entity.FeedbackEntity;
import api.scolaro.uz.entity.PersonRoleEntity;
import api.scolaro.uz.enums.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, String> {

    @Query(value = "FROM PersonRoleEntity t where t.personId=?1 and t.role = ?2 and t.visible = true")
    PersonRoleEntity findPersonRoleEnumList(String profileId, RoleEnum enums);

    @Modifying
    @Transactional
    @Query("Update FeedbackEntity set visible = false , deletedId=:deletedId, deletedDate=:deletedDate where id =:id")
    int deleted(@Param("id") String id,
                @Param("deletedId") String currentUserId,
                @Param("deletedDate") LocalDateTime now);

    Page<FeedbackEntity> getAllByVisibleIsTrueOrderByCreatedDateDesc(Pageable pageable);
}
