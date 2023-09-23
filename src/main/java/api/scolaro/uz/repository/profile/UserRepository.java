package api.scolaro.uz.repository.profile;
import api.scolaro.uz.entity.profile.UserEntity;
import api.scolaro.uz.enums.GeneralStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    Optional<UserEntity> findByPhone(String phoneNumber);

    Optional<UserEntity> findByPhoneAndVisibleIsTrue(String phone);
    @Modifying
    @Transactional
    @Query("update UserEntity set status = ?2 where  phone = ?1")
    void updateStatus(String phone, GeneralStatus active);
}
