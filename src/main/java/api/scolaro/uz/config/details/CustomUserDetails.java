package api.scolaro.uz.config.details;

import api.dean.db.entity.GeneralStatus;
import api.dean.db.entity.ProfileEntity;
import api.dean.db.enums.RoleEnum;
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
    private String name;
    private String surname;
    private String phone;
    private String password;
    private Boolean visible;
    private List<SimpleGrantedAuthority> roleList = new LinkedList<>();
    private GeneralStatus status;

    public CustomUserDetails(ProfileEntity entity, List<RoleEnum> roles) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.surname = entity.getSurname();
        this.phone = entity.getPhone();
        this.status = entity.getStatus();
        this.password = entity.getPassword();
        this.visible = entity.getVisible();
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
}
