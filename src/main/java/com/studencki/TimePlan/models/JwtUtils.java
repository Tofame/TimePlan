package com.studencki.TimePlan.models;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    static private String jwtSecret = "YEVaK93z5h8jdXFWSDDWC7QQQuvXA4P70/rZXBIJbYI=";
    static private long jwtExpirationMs = 3600000;

    static public String generateToken(String studentIndex) {
        return Jwts.builder()
                .claim("studentIndex", studentIndex)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
}