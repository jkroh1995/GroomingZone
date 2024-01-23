package tdd.groomingzone.auth.application.service;

import org.springframework.stereotype.Component;
import tdd.groomingzone.auth.application.port.out.RedisSignInPort;
import tdd.groomingzone.auth.utils.CookieManager;
import tdd.groomingzone.auth.utils.JwtManager;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class SuccessfulAuthenticationProcessor {

    private final JwtManager jwtManager;
    private final CookieManager cookieManager;
    private final RedisSignInPort redisSignInPort;

    public SuccessfulAuthenticationProcessor(JwtManager jwtManager, CookieManager cookieManager, RedisSignInPort redisSignInPort) {
        this.jwtManager = jwtManager;
        this.cookieManager = cookieManager;
        this.redisSignInPort = redisSignInPort;
    }

    public void setCookieInResponseHeader(HttpServletResponse response, MemberEntity memberEntity) {
        String accessToken = getAccessTokenFromJwtManager(memberEntity);
        String refreshToken = getRefreshTokenFromJwtManager(memberEntity);

        Cookie accessTokenCookie = cookieManager.createCookie("ACCESS_TOKEN", "Bearer+" + accessToken, jwtManager.getAccessTokenExpirationMinutes());
        Cookie refreshTokenCookie = cookieManager.createCookie("REFRESH_TOKEN", refreshToken, jwtManager.getRefreshTokenExpirationMinutes());

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        redisSignInPort.signIn(memberEntity.getEmail(), accessToken);
    }


    private String getAccessTokenFromJwtManager(MemberEntity memberEntity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", memberEntity.getEmail());
        claims.put("roles", memberEntity.getRoles());

        String subject = memberEntity.getEmail();
        Date expiration = jwtManager.getTokenExpiration(jwtManager.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey());

        return jwtManager.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }

    private String getRefreshTokenFromJwtManager(MemberEntity memberEntity) {
        String subject = memberEntity.getEmail();
        Date expiration = jwtManager.getTokenExpiration(jwtManager.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey());

        return jwtManager.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
    }
}
