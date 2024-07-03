package com.sopt.seminar.common.jwt;

import com.sopt.seminar.dto.UserJoinResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean { //넛쉘의 JwtUtil.java 와 같은 역할??

    public static final String USER_ID = "uid";
    private Key key;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expire-period}")
    private Integer accessTokenExpirePeriod;

    @Value("${jwt.refresh-token-expire-period}")
    private Integer refreshTokenExpirePeriod;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 빈 객체 초기화 시 코드 구현
        // secretKey가 이미 Base64로 인코딩 된 문자열이라고 가정
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // 이를 디코딩하여 바이트 배열로 변환
        this.key = Keys.hmacShaKeyFor(keyBytes); // HMAC SHA (서명)키 생성
    }

    public String issueAccessToken(final Authentication authentication) {
        return generateToken(authentication, accessTokenExpirePeriod);
    }

    public String issueRefreshToken(final Authentication authentication) {
        return generateToken(authentication, refreshTokenExpirePeriod);
    }

    // 토큰을 생성하는 메서드
    public String generateToken(Authentication authentication, int tokenExpirationTime) {
        final Claims claims = Jwts.claims();
        claims.put(USER_ID, authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰에서 Claim 추출하는 메서드
    private Claims getTokenBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getTokenBody(token);
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException exception) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    public Long getUserFromJwt(String token){
        Claims claims = getTokenBody(token);
        return Long.valueOf(claims.get((USER_ID)).toString()); //user Id 추출
    }
}
