package api.scolaro.uz.repository.sms;

import api.scolaro.uz.entity.sms.SmsTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SmsTokenRepository extends JpaRepository<SmsTokenEntity,String> {
    SmsTokenEntity findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update SmsTokenEntity  set token=:token where email=:email")
    int updateToken(@Param("token") String token, @Param("email") String email);
}
