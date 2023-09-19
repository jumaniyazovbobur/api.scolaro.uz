package api.scolaro.uz.repository;

import api.dean.db.entity.AttachEntity;
import api.dean.db.enums.FileType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AttachRepository extends JpaRepository<AttachEntity, String> {

    @Modifying
    @Transactional
    @Query("delete from AttachEntity where id = ?1 ")
    void delete(String id);
    Page<AttachEntity>  findAllByFileType(Pageable pageable, FileType fileType);
}