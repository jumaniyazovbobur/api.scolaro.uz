package api.scolaro.uz.repository.scholarShip;

import api.scolaro.uz.entity.scholarShip.ScholarShipDegreeTypeEntity;
import api.scolaro.uz.enums.UniversityDegreeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ScholarShipDegreeTypeRepository extends JpaRepository<ScholarShipDegreeTypeEntity, String> {
    @Query("select s.degreeType from ScholarShipDegreeTypeEntity as s where s.scholarShipId =:scholarShipId")
    List<UniversityDegreeType> getScholarShipDegreeTypeListByScholarShipId(@Param("scholarShipId") String scholarShipId);

    @Transactional
    @Modifying
    void deleteByScholarShipIdAndDegreeType(String scholarShipId, UniversityDegreeType degreeType);

    @Query("select t.degreeType from ScholarShipDegreeTypeEntity as t where t.scholarShipId =:scholarShipId")
    List<UniversityDegreeType> getScholarShipDegreeTypeList(@Param("scholarShipId") String scholarShipId);

}
