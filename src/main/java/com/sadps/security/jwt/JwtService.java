package com.sadps.security.jwt;

import com.sadps.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "THIS_IS_A_SUPER_SECRET_KEY_FOR_JWT_256_BIT_LONG_ENOUGH";
    private static final long EXPIRATION_TIME = 1000* 60 * 60;

    private SecretKey getSigninKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey((getSigninKey()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
