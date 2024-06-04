package tech.danielokoronkwo.workflexassessmentbackend.v1.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.danielokoronkwo.workflexassessmentbackend.common.config.JwtConfig;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private static final int ACCESS_TOKEN_EXPIRY = 60 * 60 * 1000; // 1 hour
    private static final int REFRESH_TOKEN_EXPIRY = 60 * 60 * 24 * 14 * 1000; // 2 weeks

    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateAccessToken(String email) {
        logger.info("Generating new access token for Subject: {}", email);
        Map<String, Object> claims = new HashMap<>();
        return createAccessToken(claims, email);
    }

    public String generateRefreshToken(String email) {
        logger.info("Generating new refresh token for Subject: {}", email);
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, email);
    }

    public boolean validateAccessToken(String accessToken, String email) {
        final String subject = getAccessTokenSubject(accessToken);
        return subject.equals(email) && !isAccessTokenExpired(accessToken);

    }

    public boolean validateRefreshToken(String refreshToken, String email) {
        final String subject = getRefreshTokenSubject(refreshToken);
        return subject.equals(email) && !isRefreshTokenExpired(refreshToken);
    }

    public String getAccessTokenSubject(String token) {
        return extractAccessTokenClaim(token, Claims::getSubject);
    }

    public String getRefreshTokenSubject(String token) {
        return extractAccessTokenClaim(token, Claims::getSubject);
    }

    public Boolean isAccessTokenExpired(String token) {
        return extractAccessTokenClaim(token, Claims::getExpiration).before(new Date());
    }

    public Boolean isRefreshTokenExpired(String token) {
        return extractRefreshTokenClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractAccessTokenClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllAccessTokenClaims(token);
        return claimsResolver.apply(claims);
    }

    public <T> T extractRefreshTokenClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllRefreshTokenClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllAccessTokenClaims(String token) {
        return Jwts.parser()
                .verifyWith(getAccessTokenSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Claims extractAllRefreshTokenClaims(String token) {
        return Jwts.parser()
                .verifyWith(getRefreshTokenSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String createAccessToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
                .signWith(getAccessTokenSigningKey())
                .compact();
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY))
                .signWith(getRefreshTokenSigningKey())
                .compact();
    }

    private SecretKey getAccessTokenSigningKey() {
        byte[] keyBytes = Decoders.BASE64
                .decode(Encoders.BASE64.encode(jwtConfig.getAccessTokenSecretKey().getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getRefreshTokenSigningKey() {
        byte[] keyBytes = Decoders.BASE64
                .decode(Encoders.BASE64.encode(jwtConfig.getRefreshTokenSecretKey().getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}