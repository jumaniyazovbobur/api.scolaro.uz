package api.scolaro.uz.config.details;

import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.repository.profile.PersonRoleRepository;

import api.scolaro.uz.repository.consulting.ConsultingRepository;
import api.scolaro.uz.repository.profile.ProfileRepository;
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
    private ConsultingRepository consultingRepository;
    @Autowired
    private PersonRoleRepository personRoleRepository;

    /**
     * Profile
     */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Optional<ProfileEntity> profileOptional = profileRepository.findByPhoneAndVisibleIsTrue(phone);
        if (profileOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        ProfileEntity profileEntity = profileOptional.get();
        List<RoleEnum> roleList = personRoleRepository.findPersonRoleEnumList(profileEntity.getId());
        return new CustomUserDetails(profileEntity, roleList);
    }

    /**
     * Consulting
     */
    public UserDetails loadConsultingByPhone(String phone) throws UsernameNotFoundException {
        Optional<ConsultingEntity> consultingOptional = consultingRepository.findByPhoneAndVisibleIsTrue(phone);
        if (consultingOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        ConsultingEntity consultingEntity = consultingOptional.get();
        List<RoleEnum> roleList = personRoleRepository.findPersonRoleEnumList(consultingEntity.getId());
        return new CustomUserDetails(consultingEntity, roleList);
    }
}
