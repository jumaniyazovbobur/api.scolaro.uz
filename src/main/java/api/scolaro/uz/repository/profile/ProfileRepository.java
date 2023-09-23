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

    Optional<ProfileEntity> findByIdAndVisibleTrue(String id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set visible=false ,deletedDate=:date where id=:id")
    void deleted(@Param("id") String id,
                 @Param("date")LocalDateTime date);


        Optional<ProfileEntity> findByPhone(String phoneNumber);

        Optional<ProfileEntity> findByPhoneAndVisibleIsTrue(String phone);

        @Modifying
        @Transactional
        @Query("update ProfileEntity set status = ?2 where  phone = ?1")
        void updateStatus(String phone, GeneralStatus active);
    }
