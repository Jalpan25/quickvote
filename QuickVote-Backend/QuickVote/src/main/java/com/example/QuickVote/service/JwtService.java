package com.example.QuickVote.service;

import com.example.QuickVote.dto.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "your-256-bit-secret-key-should-be-very-long-for-hmac";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ Generate token for AppUser (OTP users)
    public String generateToken(AppUser user) {
        Map<String, Object> claims = Map.of("role", user.getRole());
        return buildToken(claims, user.getEmail()); // email in sub
    }

    // ✅ Generate token for Admin (Spring Security UserDetails)
    public String generateToken(UserDetails userDetails) {
        // Extract first authority (e.g., ROLE_ADMIN or ROLE_SUPERADMIN)
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(auth -> auth.getAuthority().replace("ROLE_", "")) // <- strip ROLE_ prefix
                .orElse("USER");

        Map<String, Object> claims = Map.of("role", role);
        return buildToken(claims, userDetails.getUsername()); // email in sub
    }

    // 🔨 Core builder method (called internally)
    private String buildToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // 👈 email will be stored in sub
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hrs
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract email from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // email was set in subject
    }

    // ✅ Extract role from token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // ✅ Validate token for Admin/SuperAdmin
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // ✅ Validate token for AppUser
    public boolean isTokenValid(String token, AppUser user) {
        return extractUsername(token).equals(user.getEmail()) && !isTokenExpired(token);
    }

    // ⏳ Check expiration
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // 🧠 Extract any claim
    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // 🔍 Decode the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
