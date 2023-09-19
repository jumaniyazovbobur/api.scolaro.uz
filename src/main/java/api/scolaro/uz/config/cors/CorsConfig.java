package api.scolaro.uz.config.cors;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfig {
    //    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("GET","POST","PUT","DELETE");
////                        .allowedHeaders("Content-Type", "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With", "lang","LANG");
////                        .allowedHeaders("*").allowedOrigins("*").allowedOriginPatterns("*");
//            }
//        };
//    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        //configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//        configuration.setAllowedOriginPatterns(List.of("*"));
//
//        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT", "OPTIONS"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(
//                Arrays.asList(
//                        "Access-Control-Allow-Headers",
//                        "Access-Control-Allow-Origin",
//                        "Access-Control-Request-Method",
//                        "Access-Control-Request-Headers",
//                        "Origin", "Cache-Control",
//                        "Content-Type",
//                        "Authorization"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
