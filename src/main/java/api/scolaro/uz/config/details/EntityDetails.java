package api.scolaro.uz.config.details;

import api.scolaro.uz.enums.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EntityDetails {

    public static CustomUserDetails getCurrentUserDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        CustomUserDetails details = (CustomUserDetails) authentication.getPrincipal();
        return details;
    }

    public static String getCurrentUserId() {
        return Objects.requireNonNull(getCurrentUserDetail()).getId();
    }


    public static Boolean hasRole(RoleEnum requiredRole, List<String> roleList) {
        return roleList.stream().anyMatch(role -> role.equals(requiredRole.name()));
    }

    public static Boolean hasRoleCurrentUser(RoleEnum requiredRole) {
        CustomUserDetails details = getCurrentUserDetail();
        return details.getRoleList().stream().anyMatch(simpleGrantedAuthority -> simpleGrantedAuthority.getAuthority().equals(requiredRole.name()));

    }

    public static List<String> getCurrentProfileRoleList() {
        CustomUserDetails details = getCurrentUserDetail();
        assert details != null;
        return details.getRoleList().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
    }

}
