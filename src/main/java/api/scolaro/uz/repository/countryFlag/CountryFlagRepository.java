package api.scolaro.uz.repository.countryFlag;

import api.scolaro.uz.entity.place.CountryFlagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryFlagRepository extends JpaRepository<CountryFlagEntity,String> {

    List<CountryFlagEntity> findAllByVisibleTrue();
    Optional<CountryFlagEntity> findByIdAndVisibleTrue(String id);

}
