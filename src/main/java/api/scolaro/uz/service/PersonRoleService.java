package api.scolaro.uz.service;



import api.scolaro.uz.entity.PersonRoleEntity;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.repository.PersonRoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PersonRoleService {
    private final PersonRoleRepository personRoleRepository;

    public void create(String profileId, List<RoleEnum> roles) {
        roles.forEach(roleEnum -> create(profileId, roleEnum));
    }

    public void create(String personId, RoleEnum role) {
        PersonRoleEntity profileRoleEntity = new PersonRoleEntity();
        profileRoleEntity.setRole(role);
        profileRoleEntity.setPersonId(personId);
        personRoleRepository.save(profileRoleEntity);
    }

    public List<RoleEnum> getProfileRoleList(String id) {
        return personRoleRepository.findPersonRoleEnumList(id);
    }


    public void update(String profileId, List<RoleEnum> roles) {
        List<PersonRoleEntity> oldRoleList = personRoleRepository.findPersonRoleList(profileId);
        // create new role
        for (RoleEnum role : roles) {
            if (!containsRole(oldRoleList, role)) {
                create(profileId, role);
            }
        }
        // remove role
        for (PersonRoleEntity entity : oldRoleList) {
            if (!roles.contains(entity.getRole())) {
                personRoleRepository.setVisibleFalse(entity.getId(), LocalDateTime.now());
            }
        }
    }

//    public void changePersonMentorRole(String personId) {
//        List<RoleEnum> roleEnumList = personRoleRepository.findPersonRoleEnumList(personId);
//        if (roleEnumList.contains(RoleEnum.ROLE_MODERATOR)) {
//            personRoleRepository.deleteRole(personId, RoleEnum.ROLE_MODERATOR);
//        } else {
//            create(personId, RoleEnum.ROLE_MODERATOR);
//        }
//    }

    private boolean containsRole(List<PersonRoleEntity> oldRoleList, RoleEnum role) {
        for (PersonRoleEntity profileRole : oldRoleList) {
            if (profileRole.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
