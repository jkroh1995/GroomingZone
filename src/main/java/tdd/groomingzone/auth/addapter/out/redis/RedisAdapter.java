package tdd.groomingzone.auth.addapter.out.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tdd.groomingzone.auth.application.port.out.RedisSignOutPort;
import tdd.groomingzone.auth.utils.JwtManager;

import java.util.concurrent.TimeUnit;

@Service
public class RedisAdapter implements RedisSignOutPort {

    private final JwtManager jwtManager;
    private final RedisTemplate<String, String> redisTemplate;

    public RedisAdapter(JwtManager jwtManager, RedisTemplate<String, String> redisTemplate) {
        this.jwtManager = jwtManager;
        this.redisTemplate = redisTemplate;
    }

    public void signOut(String accessToken, String refreshToken) {
        jwtManager.verifyToken(accessToken, jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey()));
        redisTemplate.opsForValue().set(accessToken, "sign-out");
        redisTemplate.opsForValue().set(refreshToken, "sign-out");
        redisTemplate.expire(accessToken, jwtManager.getAccessTokenExpirationMinutes(), TimeUnit.MINUTES);
        redisTemplate.expire(refreshToken, jwtManager.getRefreshTokenExpirationMinutes(), TimeUnit.MINUTES);
    }

    public boolean isSignOut(String accessToken) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(accessToken));
    }
}
