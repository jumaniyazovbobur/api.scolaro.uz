package api.scolaro.uz.repository.attach;


import api.scolaro.uz.entity.AttachEntity;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AttachRepository extends JpaRepository<AttachEntity, String> {


    @Modifying
    @Transactional
    @Query("delete from AttachEntity where id = ?1 ")
    void delete(String id);

}