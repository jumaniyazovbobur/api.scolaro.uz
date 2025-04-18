package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.GeneralStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultingRepository extends JpaRepository<ConsultingEntity, String> {

    Optional<ConsultingEntity> findByIdAndVisibleTrue(String id);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set visible=false , deletedId=:deletedId, deletedDate=:date where id=:id")
    int deleted(@Param("id") String id,
                @Param("deletedId") String deleteId,
                @Param("date") LocalDateTime date);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set status = :status where id=:id")
    int changeStatus(@Param("id") String id,
                     @Param("status") GeneralStatus status);

    @Query(value = "select * from consulting where visible=true limit 6 ", nativeQuery = true)
    List<ConsultingEntity> getTopConsulting();

    @Query(value = "from ConsultingEntity c left join fetch c.photo WHERE c.visible = true and c.status = 'ACTIVE' ")
    @Transactional(readOnly = true)
    List<ConsultingEntity> getTopConsulting(Pageable pageable);

    @Query("select consulting from ConsultingUniversityEntity where universityId =:universityId")
    List<ConsultingEntity> getUniversityConsultingList(@Param("universityId") Long universityId);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set managerId =:managerId where id=:id")
    int updateManager(@Param("id") String id, @Param("managerId") String managerId);

    @Modifying
    @Transactional
    @Query("update ConsultingEntity set balance = balance + ?2 where id = ?1")
    int fillBalance(String consultingId, Long amount);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set status =:status where id=:id")
    int updateStatus(@Param("id") String id, @Param("status") GeneralStatus status);

}
