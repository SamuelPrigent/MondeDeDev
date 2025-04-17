package com.openclassrooms.mddapi.security;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

  @Autowired
  private Dotenv dotenv;

  private Key key;

  @PostConstruct
  public void init() {
    String secretKey = dotenv.get("JWT_SECRET_KEY");
    if (secretKey == null || secretKey.trim().isEmpty()) {
      throw new IllegalStateException("JWT_SECRET_KEY is not set in environment variables or .env file");
    }
    byte[] keyBytes = secretKey.getBytes();
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  private final long jwtExpiration = 1000 * 60 * 60 * 10; // 10 hours

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    if (userDetails instanceof org.springframework.security.core.userdetails.User) {
      // On ajoute l'email dans les claims
      claims.put("email", userDetails.getUsername());
    }
    return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)).signWith(key).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}