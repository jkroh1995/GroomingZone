package tdd.groomingzone.auth.utils;

import org.springframework.stereotype.Component;
import tdd.groomingzone.global.exception.CustomAuthenticationException;
import tdd.groomingzone.global.exception.ExceptionCode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieManager {
    public Cookie createCookie(String name, String token, int tokenExpirationMinutes) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(tokenExpirationMinutes);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie resolveAccessTokenCookie(HttpServletRequest request) {
        return resolveCookie(request, "ACCESS_TOKEN");
    }

    public Cookie resolveRefreshTokenCookie(HttpServletRequest request) {
        return resolveCookie(request, "REFRESH_TOKEN");
    }

    private Cookie resolveCookie(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    return cookie;
                }
            }
        }

        throw new CustomAuthenticationException(ExceptionCode.NOT_SIGN_IN);
    }
}
