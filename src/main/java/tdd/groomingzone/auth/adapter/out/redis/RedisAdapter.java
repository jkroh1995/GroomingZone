package tdd.groomingzone.auth.adapter.out.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import tdd.groomingzone.auth.application.port.out.RedisSignInPort;
import tdd.groomingzone.auth.application.port.out.RedisSignOutPort;
import tdd.groomingzone.auth.utils.JwtManager;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisAdapter implements RedisSignOutPort, RedisSignInPort {

    private final JwtManager jwtManager;
    private final RedisTemplate<String, String> redisTemplate;

    public RedisAdapter(JwtManager jwtManager, RedisTemplate<String, String> redisTemplate) {
        this.jwtManager = jwtManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void signOut(String accessToken, String email) {
        jwtManager.verifyToken(accessToken, jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey()));
        redisTemplate.opsForValue().getAndDelete(email);
    }

    @Override
    public void signIn(String email, String accessToken) {
        redisTemplate.opsForValue().set(email, accessToken);
        redisTemplate.expire(email, jwtManager.getAccessTokenExpirationMinutes(), TimeUnit.MINUTES);
    }

    @Override
    public boolean alreadySignIn(String email) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(email));
    }
}
