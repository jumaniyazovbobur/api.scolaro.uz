package api.scolaro.uz.repository.university;

import api.scolaro.uz.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface UniversityRepository extends JpaRepository<UniversityEntity,Long> {
    @Transactional
    @Modifying
    @Query("update UniversityEntity set visible=false , deletedId=:deletedId, deletedDate=:date where id=:id")
    int deleted(@Param("id") Long id,
                @Param("deletedId") String deleteId,
                @Param("date") LocalDateTime date);
}
