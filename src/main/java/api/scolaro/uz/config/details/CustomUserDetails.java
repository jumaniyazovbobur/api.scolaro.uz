package api.scolaro.uz.config.details;


import api.scolaro.uz.entity.consulting.ConsultingEntity;
import api.scolaro.uz.entity.ProfileEntity;
import api.scolaro.uz.entity.consulting.ConsultingProfileEntity;
import api.scolaro.uz.enums.GeneralStatus;
import api.scolaro.uz.enums.RoleEnum;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    protected String id;
    private String phone;
    private String password;
    private Boolean visible;
    private List<SimpleGrantedAuthority> roleList = new LinkedList<>();
    private GeneralStatus status;
    private String profileConsultingId; // consultingId

    public CustomUserDetails(ProfileEntity entity, List<RoleEnum> roles) {
        this.id = entity.getId();
        this.phone = entity.getPhone();
        this.status = entity.getStatus();
        this.password = entity.getPassword();
        this.visible = entity.getVisible();
        for (RoleEnum role : roles) {
            roleList.add(new SimpleGrantedAuthority(role.name()));
        }
    }

    public CustomUserDetails(ConsultingProfileEntity entity, List<RoleEnum> roles) {
        this.id = entity.getId();
        this.phone = entity.getPhone();
        this.status = entity.getStatus();
        this.password = entity.getPassword();
        this.visible = entity.getVisible();
        this.profileConsultingId = entity.getConsultingId();
        for (RoleEnum role : roles) {
            roleList.add(new SimpleGrantedAuthority(role.name()));
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setProfileConsultingId(String profileConsultingId) {
        this.profileConsultingId = profileConsultingId;
    }
}
