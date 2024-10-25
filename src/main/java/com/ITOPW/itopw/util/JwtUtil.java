package com.ITOPW.itopw.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 안전한 비밀 키 생성
    private final long EXPIRATION_TIME = 3600000; // 1시간 (밀리초 단위)

    // JWT 생성 메서드
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey) // 안전한 비밀 키 사용
                .compact();
    }

    // JWT 검증 메서드 (선택 사항)
    public boolean validateToken(String token, String userId) {
        final String username = extractSubject(token);
        return (username.equals(userId) && !isTokenExpired(token));
    }

    // JWT에서 사용자 ID 추출
    public String extractSubject(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰의 유효성 검사
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
