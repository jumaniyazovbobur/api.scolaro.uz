package api.scolaro.uz.repository;

import api.scolaro.uz.entity.FacultyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FacultyRepository extends CrudRepository<FacultyEntity, String> {
    List<FacultyEntity> getAllByVisibleIsTrueOrderByCreatedDateDesc();
}
