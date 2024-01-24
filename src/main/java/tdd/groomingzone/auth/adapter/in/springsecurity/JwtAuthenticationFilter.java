package tdd.groomingzone.auth.adapter.in.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tdd.groomingzone.auth.application.port.out.RedisSignInPort;
import tdd.groomingzone.auth.application.service.SuccessfulAuthenticationProcessor;
import tdd.groomingzone.global.exception.CustomAuthenticationException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RedisSignInPort redisSignInPort;
    private final SuccessfulAuthenticationProcessor successfulAuthenticationProcessor;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RedisSignInPort redisSignInPort, SuccessfulAuthenticationProcessor successfulAuthenticationProcessor) {
        this.authenticationManager = authenticationManager;
        this.redisSignInPort = redisSignInPort;
        this.successfulAuthenticationProcessor = successfulAuthenticationProcessor;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request
            , HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        SignInDto signInDto = objectMapper.readValue(request.getInputStream(), SignInDto.class);

        if(redisSignInPort.alreadySignIn(signInDto.getEmail())){
            throw new CustomAuthenticationException(ExceptionCode.ALREADY_SIGN_IN);
        }

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

        successfulAuthenticationProcessor.setCookieInResponseHeader(response, memberEntity);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
