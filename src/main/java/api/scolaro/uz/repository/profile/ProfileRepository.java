package api.scolaro.uz.repository.profile;


import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.enums.GeneralStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByPhone(String phone);

    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String username);


    @Transactional
    @Modifying
    @Query("update ProfileEntity set password =:nPswd where id =:id")
    int updatePassword(@Param("id") String id, @Param("nPswd") String nPswd);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set name =:name, surname=:surname where id =:id")
    int updateDetail(@Param("id") String id, @Param("name") String name, @Param("surname") String surname);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set status = ?2 where  phone = ?1")
    void updateStatus(String phone, GeneralStatus active);

    Optional<ProfileEntity> findByPhoneAndVisibleIsTrue(String phone);


    Optional<ProfileEntity> findByIdAndVisibleTrue(String id);
    Optional<ProfileEntity> findByNickName(String nickName);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false, deletedId=:deletedId, deletedDate=:date where id=:id")
    int deleted(@Param("id") String id,
                @Param("deletedId") String deletedId,
                @Param("date") LocalDateTime date);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where id=:id")
    int changeStatus(@Param("id") String id,
                     @Param("status") GeneralStatus status);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set tempPhone = :newPhone, smsCode=:code where id=:id")
    void changeNewPhone(@Param("id") String id,
                        @Param("newPhone") String newPhone,
                        @Param("code") String code);
    @Transactional
    @Modifying
    @Query("update ProfileEntity set phone = :phone where id=:id")
    int changePhone(@Param("id") String id,
                    @Param("phone") String phone);
}
