package tdd.groomingzone.auth.utils;

import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class JwtManagerTest {

    private final JwtManager jwtManager = new JwtManager();
    private final String secretKey = "kevin1234123412341234123412341234";
    private final String base64EncodedSecretKey = jwtManager.encodeBase64SecretKey(secretKey);

    @Test
    public void encodeBase64SecretKeyTest() {
        System.out.println(base64EncodedSecretKey);

        assertThat(secretKey).isEqualTo(new String(Decoders.BASE64.decode(base64EncodedSecretKey)));
    }

    @Test
    public void generateAccessTokenTest() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("roles", List.of("CUSTOMER"));

        String subject = "test access token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        Date expiration = calendar.getTime();

        String accessToken = jwtManager.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        System.out.println(accessToken);

        assertThat(accessToken).isNotNull();
    }

    @Test
    public void generateRefreshTokenTest() {
        String subject = "test refresh token";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        Date expiration = calendar.getTime();

        String refreshToken = jwtManager.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        System.out.println(refreshToken);

        assertThat(refreshToken).isNotNull();
    }
}