package api.scolaro.uz.config.jwt;


import api.scolaro.uz.config.SecurityConfiguration;
import api.scolaro.uz.config.details.CustomUserDetailsService;
import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.JwtDTO;
import api.scolaro.uz.enums.RoleEnum;
import api.scolaro.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays
                .stream(SecurityConfiguration.AUTH_WHITELIST)
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        String tokenHeader = request.getParameter("token");
        boolean tokenIsHeader = tokenHeader != null;
        if (header == null || !header.startsWith("Bearer ")) {
            if (!tokenIsHeader) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {
            final String token = tokenIsHeader ? tokenHeader : header.substring(7).trim();
            JwtDTO jwtDTO = JwtUtil.decode(token);
            // load user depending on role
            UserDetails userDetails;
            String phone = jwtDTO.getPhone();
            if (EntityDetails.hasRole(RoleEnum.ROLE_CONSULTING, jwtDTO.getRoleList())) {  // load consulting
                userDetails = userDetailsService.loadConsultingByPhone(phone);
            } else { // load student or admin
                userDetails = userDetailsService.loadUserByUsername(phone);
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (JwtException | UsernameNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

    }
}
