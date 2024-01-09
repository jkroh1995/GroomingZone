package tdd.groomingzone.auth.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieProvider {
    public Cookie createCookie(String name, String token, int tokenExpirationMinutes) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(tokenExpirationMinutes);
        cookie.setPath("/");
        return cookie;
    }
}
