package api.scolaro.uz.repository;

import api.dean.db.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity,Integer> {
    List<RegionEntity> getAllByVisibleIsTrueOrderByCreatedDateDesc();

}
