package tdd.groomingzone.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tdd.groomingzone.auth.dto.SignInDto;
import tdd.groomingzone.auth.utils.JwtManager;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtManager jwtManager) {
        this.authenticationManager = authenticationManager;
        this.jwtManager = jwtManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request
            , HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        SignInDto signInDto = objectMapper.readValue(request.getInputStream(), SignInDto.class);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        MemberEntity memberEntity = (MemberEntity) authResult.getPrincipal();

        String accessToken = getAccessTokenFromJwtTokenizer(memberEntity);
        String refreshToken = getRefreshTokenFromJwtTokenizer(memberEntity);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);
        response.setIntHeader("MemberId", (int) memberEntity.getId());

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    private String getAccessTokenFromJwtTokenizer(MemberEntity memberEntity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", memberEntity.getEmail());
        claims.put("roles", memberEntity.getRoles());

        String subject = memberEntity.getEmail();
        Date expiration = jwtManager.getTokenExpiration(jwtManager.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey());

        return jwtManager.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }

    private String getRefreshTokenFromJwtTokenizer(MemberEntity memberEntity) {
        String subject = memberEntity.getEmail();
        Date expiration = jwtManager.getTokenExpiration(jwtManager.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey());

        return jwtManager.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
    }
}