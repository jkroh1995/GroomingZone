package tdd.groomingzone.auth.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tdd.groomingzone.auth.application.port.out.RedisSignOutPort;
import tdd.groomingzone.auth.utils.JwtManager;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignOutServiceTest {

    @Mock
    private RedisSignOutPort signOutPort;

    @Mock
    private JwtManager jwtManager;

    @InjectMocks
    private SignOutService signOutService;

    @Test
    @DisplayName("로그아웃 요청을 보내면 로그아웃에 성공한다.")
    void signOut() {
        String testAccessToken = "test access token";
        String testRefreshToken = "test refresh token";

        given(jwtManager.getTokenFromHeader(anyString())).willReturn(testAccessToken);

        boolean isAlreadySignOut = false;
        given(signOutPort.isSignOut(anyString())).willReturn(isAlreadySignOut);

        signOutService.signOut(testAccessToken, testRefreshToken);

        verify(signOutPort).signOut(testAccessToken, testRefreshToken);
    }
}