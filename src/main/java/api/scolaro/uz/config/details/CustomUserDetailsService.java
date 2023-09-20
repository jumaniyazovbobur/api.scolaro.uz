package api.scolaro.uz.config.details;


import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.repository.PersonRoleRepository;
import api.scolaro.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PersonRoleRepository personRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> profileOptional = profileRepository.findByPhone(username);
        if (profileOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        ProfileEntity profileEntity = profileOptional.get();
        List<RoleEnum> roleList = personRoleRepository.findPersonRoleEnumList(profileEntity.getId());
        return new CustomUserDetails(profileEntity, roleList);
    }
}
