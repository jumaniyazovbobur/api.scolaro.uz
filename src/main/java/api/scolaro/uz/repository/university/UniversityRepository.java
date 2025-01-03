package api.scolaro.uz.repository.university;

import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingUniversityEntity;
import api.scolaro.uz.enums.GeneralStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {
    @Transactional
    @Modifying
    @Query("update UniversityEntity set visible=false , deletedId=:deletedId, deletedDate=:date where id=:id")
    int deleted(@Param("id") Long id,
                @Param("deletedId") String deleteId,
                @Param("date") LocalDateTime date);

    @Query(value = "select * from university where visible = true order by rating limit 10 ", nativeQuery = true)
    List<UniversityEntity> getTopUniversity();

    // left join fetch u.photo and jpql
    @Query(value = "from UniversityEntity u  where u.visible = true and u.status = 'ACTIVE' order by u.rating ")
    List<UniversityEntity> getTopUniversity(Pageable pageable);

    @Query("select  university from ConsultingUniversityEntity where consultingId =:consultingId and university.status = 'ACTIVE' ")
    List<UniversityEntity> getUniversityListByConsultingId(@Param("consultingId") String consultingId);

    @Transactional
    @Modifying
    @Query("update UniversityEntity set status = :status where id=:id")
    int changeStatus(@Param("id") Long id,
                     @Param("status") GeneralStatus status);
}
