package api.scolaro.uz.repository;

import api.dean.db.entity.PersonRoleEntity;
import api.dean.db.enums.RoleEnum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonRoleRepository extends CrudRepository<PersonRoleEntity, String> {
    @Query(value = "SELECT t.role FROM PersonRoleEntity t where t.personId=?1 and t.visible = true")
    List<RoleEnum> findPersonRoleEnumList(String profileId);

    @Query(value = "FROM PersonRoleEntity t where t.personId=?1 and t.visible = true")
    List<PersonRoleEntity> findPersonRoleList(String profileId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PersonRoleEntity t SET t.visible = false, t.deletedDate =?2  where t.id=?1")
    List<RoleEnum> setVisibleFalse(String profileRoleEntityId, LocalDateTime deletedDate);

    @Modifying
    @Transactional
    @Query(value = "delete from PersonRoleEntity  where personId =?1 and role =?2")
    void deleteRole(String personId, RoleEnum role);
}
