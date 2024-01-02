package tdd.groomingzone.auth.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tdd.groomingzone.auth.application.port.in.usecase.SignOutUseCase;
import tdd.groomingzone.auth.addapter.out.redis.RedisAdapter;
import tdd.groomingzone.auth.application.port.out.RedisSignOutPort;
import tdd.groomingzone.auth.utils.JwtManager;
import tdd.groomingzone.global.exception.CustomAuthenticationException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Service
@Slf4j
public class SignOutService implements SignOutUseCase {

    private final RedisSignOutPort signOutPort;
    private final JwtManager jwtManager;

    public SignOutService(RedisSignOutPort signOutPort, JwtManager jwtManager) {
        this.signOutPort = signOutPort;
        this.jwtManager = jwtManager;
    }

    @Override
    public void signOut(String accessToken, String refreshToken) {
        accessToken = jwtManager.getTokenFromHeader(accessToken);
        if (signOutPort.isSignOut(accessToken)) {
            throw new CustomAuthenticationException(ExceptionCode.ALREADY_SIGN_OUT);
        }
        signOutPort.signOut(accessToken, refreshToken);
    }
}
