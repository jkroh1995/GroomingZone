package tdd.groomingzone.auth.utils;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieManager {

    private static final int MAKE_SEC_TO_MIN = 60;

    public Cookie createCookie(String name, String token, int tokenExpirationMinutes) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(tokenExpirationMinutes * MAKE_SEC_TO_MIN);
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

        return null;
    }
}
