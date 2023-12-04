package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.entity.UniversityEntity;
import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.enums.GeneralStatus;
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
    @Query("update ConsultingEntity set visible=false , deletedId=:deletedId, deletedDate=:date, status='NOT_ACTIVE' where id=:id")
    int deleteAccount(@Param("id") String id,
                      @Param("deletedId") String deleteId,
                      @Param("date") LocalDateTime date);

    Optional<ConsultingEntity> findByPhoneAndVisibleIsTrue(String phone);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set password =:nPswd where id =:id")
    int updatePassword(@Param("id") String id, @Param("nPswd") String nPswd);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set tempPhone = :newPhone, smsCode=:code where id=:id")
    void changeNewPhone(@Param("id") String id,
                        @Param("newPhone") String newPhone,
                        @Param("code") String code);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set phone = :phone where id=:id")
    int changePhone(@Param("id") String id,
                    @Param("phone") String phone);

    Optional<ConsultingEntity> findByPhone(String username);

    @Transactional
    @Modifying
    @Query("update ConsultingEntity set status = :status where id=:id")
    int changeStatus(@Param("id") String id,
                     @Param("status") GeneralStatus status);

    @Query(value = "select * from consulting where visible=true limit 6 ", nativeQuery = true)
    List<ConsultingEntity> getTopConsulting();

    @Query("select consulting from ConsultingUniversityEntity where universityId =:universityId")
    List<ConsultingEntity> getUniversityConsultingList(@Param("universityId") Long universityId);
}
