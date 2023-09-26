package api.scolaro.uz.repository;

import api.dean.db.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer> {
//    DistrictEntity findByRegionId(Long id);

    List<DistrictEntity> findAllByRegionId(Integer regionId);
}
