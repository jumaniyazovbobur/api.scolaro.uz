package api.scolaro.uz.config.details;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

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


}
