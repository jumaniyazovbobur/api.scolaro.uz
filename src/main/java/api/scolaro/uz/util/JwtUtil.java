package api.scolaro.uz.util;


import api.scolaro.uz.dto.JwtDTO;
import api.scolaro.uz.enums.RoleEnum;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24 * 7; // 7-day
    private static final String secretKey = "dean&sdfd534hguz-mazgi";


    public static String encode(String id, String phone, List<RoleEnum> roleList) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);
        jwtBuilder.claim("id", id);
        jwtBuilder.claim("phone", phone);
        String roles = roleList.stream().map(Enum::name).collect(Collectors.joining(","));
        jwtBuilder.claim("roles", roles);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("Java backend");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getBody();
        String id = (String) claims.get("id");
        String phone = (String) claims.get("phone");
        return new JwtDTO(id, phone);
    }

}
