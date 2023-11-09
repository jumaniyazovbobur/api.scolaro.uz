package api.scolaro.uz.repository.simpleMessage;

import api.scolaro.uz.entity.SimpleMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SimpleMessageRepository extends JpaRepository<SimpleMessageEntity, String> {


/*
    @Query(value = "select * from simple_message as s " +
            " where s.application_id=:applicationId and visible=true " +
            " order by s.created_date desc ", nativeQuery = true)
    List<SimpleMessageEntity> getSimpleMessageListByApplicationId(@Param("applicationId") String id);
*/

    @Query("FROM SimpleMessageEntity as s where s.appApplicationId=:applicationId and s.visible=true " +
            "order by s.createdDate desc ")
    List<SimpleMessageEntity> getSimpleMessageListByApplicationId(@Param("applicationId") String id);


    @Modifying
    @Transactional
    @Query("update SimpleMessageEntity s set s.isStudentRead = true where s.appApplicationId = ?1 and s.isStudentRead=false and s.visible=true ")
    int updateIsStudentRead(String id);

    @Modifying
    @Transactional
    @Query("update SimpleMessageEntity s set s.isConsultingRead = true where s.appApplicationId = ?1 and s.isConsultingRead=false and s.visible=true ")
    int updateIsConsultingRead(String id);
}
