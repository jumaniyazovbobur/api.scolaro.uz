package api.scolaro.uz.repository.consulting;

import api.scolaro.uz.entity.consulting.ConsultingProfileEntity;
import api.scolaro.uz.enums.GeneralStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConsultingProfileRepository extends CrudRepository<ConsultingProfileEntity, String> {
    Optional<ConsultingProfileEntity> findByPhoneAndVisibleIsTrue(String phone);

    Optional<ConsultingProfileEntity> findByIdAndVisibleTrue(String id);

    @Transactional
    @Modifying
    @Query("update ConsultingProfileEntity set password =:nPswd where id =:id")
    int updatePassword(@Param("id") String id, @Param("nPswd") String nPswd);

    @Transactional
    @Modifying
    @Query("update ConsultingProfileEntity set tempPhone = :newPhone, smsCode=:code where id=:id")
    void changeNewPhone(@Param("id") String id,
                        @Param("newPhone") String newPhone,
                        @Param("code") String code);

    @Transactional
    @Modifying
    @Query("update ConsultingProfileEntity set phone = :phone where id=:id")
    int changePhone(@Param("id") String id,
                    @Param("phone") String phone);

    @Transactional
    @Modifying
    @Query("update ConsultingProfileEntity set visible=false , deletedId=:deletedId, deletedDate=:date, status='NOT_ACTIVE' where id=:id")
    int deleteAccount(@Param("id") String id,
                      @Param("deletedId") String deleteId,
                      @Param("date") LocalDateTime date);

    @Query(value = "select * from consulting_profile where consulting_id=:consultingId and id in (select person_id from person_role where role = 'ROLE_CONSULTING_MANAGER') ", nativeQuery = true)
    Optional<ConsultingProfileEntity> findConsultingManagerByConsultingId(@Param("consultingId") String consultingId);

    @Query("update ConsultingProfileEntity set visible = ?2 where id = ?1")
    @Modifying
    @Transactional
    int updateVisible(String id, Boolean b);

    Page<ConsultingProfileEntity> findAllByConsultingIdAndVisibleIsTrue(String consultingId,Pageable pageable);
    @Transactional
    @Modifying
    @Query("update ConsultingProfileEntity set status = ?2 where id = ?1 ")
    int updateStatus(String id, GeneralStatus status);

    @Query("update ConsultingProfileEntity set isOnline = ?2 where id = ?1")
    @Transactional
    @Modifying
    int updateIsOnline(String id, Boolean b);
}
