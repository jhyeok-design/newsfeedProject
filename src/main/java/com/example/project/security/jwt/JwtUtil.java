package com.example.project.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // TODO: 시크릿 키 어떻게 할 지 (현재 하드코딩 .. 도 아닌 음 ..)
    private static final String SECRET = "secret"; // 시크릿 키
    // TODO: 유효 시간 짧게 변경 (테스트 아직 안 함)
    private static final long TOKEN_EXPIRE_TIME = 60 * 60 * 1000L; // 토큰 유효 기간

    // 서명 키 생성
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // JWT 생성
    public String generateToken(Long userId) {

        // 토큰 만료 시간
        Date now = new Date();
        Date expiry = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // userId 호출
    public Long getUserIdFromToken(String token) {
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT 파싱
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}