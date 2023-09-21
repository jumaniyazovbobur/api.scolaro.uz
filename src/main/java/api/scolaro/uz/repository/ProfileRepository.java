package api.scolaro.uz.repository;


import api.scolaro.uz.entity.profile.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByPhone(String phone);

    Optional<UserEntity> findByPhoneAndPassword(String phone, String username);

    @Transactional
    @Modifying
    @Query("update UserEntity set password =:nPswd where id =:id")
    int updatePassword(@Param("id") String id, @Param("nPswd") String nPswd);

    @Transactional
    @Modifying
    @Query("update UserEntity set name =:name, surname=:surname where id =:id")
    int updateDetail(@Param("id") String id, @Param("name") String name, @Param("surname") String surname);
}
