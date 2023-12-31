package api.scolaro.uz.repository.place;


import api.scolaro.uz.entity.place.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity,Integer> {
    List<RegionEntity> getAllByVisibleIsTrueOrderByCreatedDateDesc();

}
