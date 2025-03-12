package com.example.spring14.util;


import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//로직을 처리한다는 의미에서 서비스어노테이션 붙임! => bean이 됐네
@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //키값도 일치하는지 다 검증하고있는거여!
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) //token 발급시 서명했던 키값도 일치한느지 확인도 된다.//일치하지않으면 예외처리되겠지
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, Map<String, Object> extraClaims) {
        Map<String, Object> claims = new HashMap<>(extraClaims);
        return createToken(claims, username);
    }

    //토큰 긴 문자열은 Jwts.builder()를 이용해서, 여기서 얻어진다!
    //토큰에 담긴 추가정보 = claims //토큰에 담긴 주요정보 = subject
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) //추가정보
                .setSubject(subject) //주요정보(주로 userName)
                .setIssuer("your-issuer") // 추가된 issuer(발급한 서비스명)
                .setIssuedAt(new Date(System.currentTimeMillis())) //발급시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) //파기되는시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) //HS256 알고리즘으로 서명
                .compact();
    }
    //token 검증하는 메소드
    public boolean validateToken(String token) {
    	//token에서 claims정보를 얻을수잇다.
    	//token에 담긴 추가 정보를 얻어낼수있다.(role, issuer등등)
    	Claims claims = extractAllClaims(token);
    	//토큰 유효기간이 남아 있는지와 issuer 정보도 일치하는지 확인해서
    	boolean isValid=!isTokenExpired(token) && "your-issuer".equals(claims.getIssuer());
    	//유효성여부를 리턴한다.
    	return isValid;

    }
}
