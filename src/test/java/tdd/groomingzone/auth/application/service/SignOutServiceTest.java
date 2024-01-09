package tdd.groomingzone.auth.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import tdd.groomingzone.auth.application.port.out.RedisSignOutPort;
import tdd.groomingzone.auth.utils.CookieProvider;
import tdd.groomingzone.auth.utils.JwtManager;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignOutServiceTest {

    private CookieProvider cookieProvider = new CookieProvider();

    @Mock
    private RedisSignOutPort signOutPort;

    @Mock
    private JwtManager jwtManager;

    @InjectMocks
    private SignOutService signOutService;

    @Test
    @DisplayName("로그아웃 요청을 보내면 로그아웃에 성공한다.")
    @WithUserDetails(value = "jk@gmail.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void signOut() {
        String testAccessToken = "test access token";
        String testRefreshToken = "test refresh token";
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Cookie accessTokenCookie = cookieProvider.createCookie("ACCESS_TOKEN", testAccessToken, 1);
        Cookie refreshTokenCookie = cookieProvider.createCookie("REFRESH_TOKEN", testRefreshToken, 1);
        mockHttpServletRequest.setCookies(accessTokenCookie, refreshTokenCookie);

        given(jwtManager.resolveAccessToken(any())).willReturn(accessTokenCookie);
        given(jwtManager.resolveRefreshToken(any())).willReturn(refreshTokenCookie);

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        signOutService.signOut("jk@gmail.com", mockHttpServletRequest, mockHttpServletResponse);

        verify(jwtManager).resolveAccessToken(mockHttpServletRequest);
        verify(jwtManager).resolveRefreshToken(mockHttpServletRequest);
        verify(signOutPort).signOut(testAccessToken, "jk@gmail.com");

        assertThat(mockHttpServletResponse.getCookie("ACCESS_TOKEN").getMaxAge()).isEqualTo(0);
        assertThat(mockHttpServletResponse.getCookie("REFRESH_TOKEN").getMaxAge()).isEqualTo(0);
    }
}