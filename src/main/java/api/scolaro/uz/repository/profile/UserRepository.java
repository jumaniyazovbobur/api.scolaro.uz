package api.scolaro.uz.repository.profile;



import api.scolaro.uz.entity.profile.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByPhone(String phoneNumber);
}
