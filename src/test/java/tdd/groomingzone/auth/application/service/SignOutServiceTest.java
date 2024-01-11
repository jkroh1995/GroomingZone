package tdd.groomingzone.auth.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import tdd.groomingzone.auth.application.port.out.RedisSignOutPort;
import tdd.groomingzone.auth.utils.CookieManager;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignOutServiceTest {

    @Mock
    private CookieManager cookieManager;

    @Mock
    private RedisSignOutPort signOutPort;

    @InjectMocks
    private SignOutService signOutService;

    @Test
    @DisplayName("로그아웃 요청을 보내면 로그아웃에 성공한다.")
    void signOut() {
        String testAccessToken = "test access token";
        String testRefreshToken = "test refresh token";
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Cookie accessTokenCookie = new Cookie("ACCESS_TOKEN", testAccessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(1);
        accessTokenCookie.setPath("/");
        Cookie refreshTokenCookie = new Cookie("REFRESH_TOKEN", testRefreshToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(1);
        accessTokenCookie.setPath("/");
        mockHttpServletRequest.setCookies(accessTokenCookie, refreshTokenCookie);

        given(cookieManager.resolveAccessTokenCookie(any())).willReturn(accessTokenCookie);
        given(cookieManager.resolveRefreshTokenCookie(any())).willReturn(refreshTokenCookie);

        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        signOutService.signOut("jk@gmail.com", mockHttpServletRequest, mockHttpServletResponse);

        verify(cookieManager).resolveAccessTokenCookie(mockHttpServletRequest);
        verify(cookieManager).resolveRefreshTokenCookie(mockHttpServletRequest);
        verify(signOutPort).signOut(testAccessToken, "jk@gmail.com");

        assertThat(mockHttpServletResponse.getCookie("ACCESS_TOKEN").getMaxAge()).isEqualTo(0);
        assertThat(mockHttpServletResponse.getCookie("REFRESH_TOKEN").getMaxAge()).isEqualTo(0);
    }
}