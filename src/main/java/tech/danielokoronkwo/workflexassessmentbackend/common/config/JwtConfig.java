package tech.danielokoronkwo.workflexassessmentbackend.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${spring.access.token.jwt.secret")
    private String accessTokenSecretKey;

    @Value("${spring.refresh.token.jwt.secret}")
    private String refreshTokenSecretKey;


    public JwtConfig() {
    }

    public JwtConfig(String accessTokenSecretKey, String refreshTokenSecretKey) {
        this.accessTokenSecretKey = accessTokenSecretKey;
        this.refreshTokenSecretKey = refreshTokenSecretKey;
    }

    public String getAccessTokenSecretKey() {
        return accessTokenSecretKey;
    }

    public String getRefreshTokenSecretKey() {
        return refreshTokenSecretKey;
    }

}