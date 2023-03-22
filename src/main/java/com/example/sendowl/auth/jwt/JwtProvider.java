package com.example.sendowl.auth.jwt;

import com.example.sendowl.auth.PrincipalDetailsService;
import com.example.sendowl.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor // final , notNull 필드에 생성자 자동생성
@Component
public class JwtProvider {
    public static final Long ACCESS_TOKEN_VALID_MILLISECOND = 1000L * 60 * 60; // access 토큰 만료 시간
    public static final int REFRESH_TOKEN_VALIDSECOND = 60 * 60 * 24; // refresh 토큰 만료 시간
    private final PrincipalDetailsService principalDetailsService;
    // @Value("${secretKey}") 일단 임시로
    private String secretKey = "secretKey";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // 키를 들고와서 Base64로 변환
    }

    public String createAccessToken(User user) {
        //user 구분을 위해 Claim에 User Pk값 넣어줌
        Claims claims = Jwts.claims().setSubject(user.getEmail() + "/" + user.getTransactionId());
        claims.put("roles", user.getRole());
        claims.put("type", "access");
        // 생성날짜, 만료 날짜를 위한 Date
        Date now = new Date();
        return Jwts.builder()// 토큰에 다양한 데이터를 넣고 압축한다.
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_MILLISECOND))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createInfiniteAccessToken(User user) {
        //user 구분을 위해 Claim에 User Pk값 넣어줌
        Claims claims = Jwts.claims().setSubject(user.getEmail() + "/" + user.getTransactionId());
        claims.put("roles", user.getRole());
        claims.put("type", "access");
        // 생성날짜, 만료 날짜를 위한 Date
        Date now = new Date();
        return Jwts.builder()// 토큰에 다양한 데이터를 넣고 압축한다.
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (1000L * 60 * 60 * 24 * 365 * 10)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    // Jwt로 인증정보를 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt에서 회원 구분 Pk 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // HTTP Request의 Header에서 Token Parsing->"X-AUTH-TOKEN: jwt" // 인증 토큰 빼내기
    public String resolveToken(HttpServletRequest request, String type) {
        String token = null;
        try {
            String bearer = request.getHeader("Authorization"); // 인증 토큰을 받는다.
            token = bearer.substring(type.length()).trim();// 토큰의 prefix를 제거하고 반환한다.
        } catch (Exception e) {
        }
        return token;
    }

    // jwt의 유효성 및 만료일자 확인
    public boolean validationToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
