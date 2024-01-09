package tdd.groomingzone.auth.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.auth.application.port.in.usecase.SignOutUseCase;
import tdd.groomingzone.auth.application.port.out.RedisSignOutPort;
import tdd.groomingzone.auth.utils.CookieManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SignOutService implements SignOutUseCase {

    private final RedisSignOutPort signOutPort;
    private final CookieManager cookieManager;

    public SignOutService(RedisSignOutPort signOutPort, CookieManager cookieManager) {
        this.signOutPort = signOutPort;
        this.cookieManager = cookieManager;
    }

    @Override
    public void signOut(String email, HttpServletRequest request, HttpServletResponse response) {
        Cookie accessTokenCookie = cookieManager.resolveAccessTokenCookie(request);
        Cookie refreshTokenCookie = cookieManager.resolveRefreshTokenCookie(request);

        expireCookie(accessTokenCookie);
        expireCookie(refreshTokenCookie);

        signOutPort.signOut(accessTokenCookie.getValue().replace("Bearer+", ""), email);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private void expireCookie(Cookie cookie) {
        cookie.setMaxAge(0);
        cookie.setPath("/");
    }
}