package api.scolaro.uz.config;


import api.scolaro.uz.config.jwt.JwtAuthenticationFilter;
import api.scolaro.uz.util.MD5Util;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtTokenFilter;

    private final UserDetailsService userDetailsService;

    public static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/api/v1/auth/**",
            "/api/v1/attach/open/*",
            "/api/v1/attach/open/v2/*",
            "/api/v1/attach/download/**",
            "/api/v1/country/public/**",
            "/api/v1/university/filter",
            "/api/v1/university/top-university",
            "/api/v1/consulting/top-consulting",
            "/api/v1/scholar-ship/top-grant",
            "/api/v1/scholar-ship/filter",
            "/api/v1/scholar-ship/*/detail",
            "/api/v1/consulting/top-consulting/*",
            "/api/v1/continent/public/**",
            "/api/v1/program/public/**",
            "/api/v1/university/*/detail",
            "/api/v1/faculty/public/**",
            "/api/v1/consulting/public/**",
            "/api/v1/destination/public/**",
            "/api/v1/web-student/public/**",
    };


    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication (login,password)
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/api/v1/attach/upload**").hasAnyRole("ADMIN", "STUDENT", "CONSULTING")
                        .requestMatchers("/api/v1/continent/public/**").hasAnyRole("ADMIN", "STUDENT", "CONSULTING")
                        .requestMatchers("/api/v1/continent-country/public/**").hasAnyRole("ADMIN", "STUDENT", "CONSULTING")
                        .requestMatchers(HttpMethod.GET, "/api/v1/consulting/comment/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/search").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/transaction/student/payme-callback").permitAll()
                        .requestMatchers("/chat-websocket/**").permitAll()
                        .anyRequest().authenticated()
        ).addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        ;
        // .cors(AbstractHttpConfigurer::disable);
        http.cors(httpSecurityCorsConfigurer -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOriginPatterns(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
//            configuration.setAllowCredentials(true);


            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            httpSecurityCorsConfigurer.configurationSource(source);
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5 = MD5Util.getMd5(rawPassword.toString());
                return md5.equals(encodedPassword);
            }
        };
    }

}
