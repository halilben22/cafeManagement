package com.halildev.cafeManagement.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    public String SECRET_KEY = "eyJhbGciOiJIUzI1NiJ9eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTcwNTYxMjM5MCwiaWF0IjoxNzA1NjEyMzkwfQdr8JApvGYvlmxFiRCnGYS8ybnATZmD9Lw0qX0U4WbAs";

    public String extractUsername(String token) {

        return extractClaims(token, Claims::getSubject);
    }


    public Date extractExpiration(String token) {


        return extractClaims(token, Claims::getExpiration);
    }


    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }


    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder().
                setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))

                .signWith(generateKey(), SignatureAlgorithm.HS256).compact();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }
    private Key generateKey() {

        byte[] key = Decoders.BASE64URL.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(key);
    }


}
