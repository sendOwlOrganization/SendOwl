package com.example.sendowl.util;

import com.example.sendowl.entity.user.Role;
import com.example.sendowl.entity.user.User;
import com.example.sendowl.service.CustomUserDetailService;
import com.example.sendowl.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor // final , notNull 필드에 생성자 자동생성
@Component
public class JwtProvider {
    // @Value("${secretKey}") 일단 임시로
    private String secretKey = "secretKey";

    private Long tokenValidMillisecond = 60* 60 * 1000L; // 토큰 만료 시간

    private final CustomUserDetailService customUserDetailService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // 키를 들고와서 Base64로 변환
    }

    public String createToken(String userPk, Role roles){
        //user 구분을 위해 Claim에 User Pk값 넣어줌
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);

        // 생성날짜, 만료 날짜를 위한 Date
        Date now = new Date();

        return Jwts.builder()// 토큰에 다양한 데이터를 넣고 압축한다.
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Jwt로 인증정보를 조회
    public Authentication getAuthentication (String token){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    // Jwt에서 회원 구분 Pk 추출
    public String getUserPk(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    // HTTP Request의 Header에서 Token Parsing->"X-AUTH-TOKEN: jwt" // 인증 토큰 빼내기
    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    // jwt의 유효성 및 만료일자 확인
    public boolean validationToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }
}
