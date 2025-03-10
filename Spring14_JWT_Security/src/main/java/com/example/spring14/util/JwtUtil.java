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

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
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
    public Boolean validateToken(String token) {
    	//token 으로부터 userName을 얻어낼수있다. 
        final String username = extractUsername(token);
        //token에 담긴 추가 정보를 얻어낼수있다. (role, issier, email등등)
        final Claims claims = extractAllClaims(token);

        return ( !isTokenExpired(token) &&
                "your-issuer".equals(claims.getIssuer())); // 추가 검증 (토큰을 교차로 쓰지 못하도록, 서비스가 맞는지까지확인)
    }
}
