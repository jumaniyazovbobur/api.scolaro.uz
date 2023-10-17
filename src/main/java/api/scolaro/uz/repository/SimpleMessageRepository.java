package api.scolaro.uz.repository;

import api.scolaro.uz.entity.SimpleMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleMessageRepository extends JpaRepository<SimpleMessageEntity,String> {
}
