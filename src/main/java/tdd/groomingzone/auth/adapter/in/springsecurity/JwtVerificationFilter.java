package tdd.groomingzone.auth.adapter.in.springsecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import tdd.groomingzone.auth.utils.CookieManager;
import tdd.groomingzone.auth.utils.JwtManager;
import tdd.groomingzone.auth.utils.CustomAuthorityUtils;
import tdd.groomingzone.global.exception.CustomAuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final CustomAuthorityUtils authorityUtils;
    private final UserDetailsService userDetailsService;
    private final CookieManager cookieManager;

    public JwtVerificationFilter(JwtManager jwtManager, CustomAuthorityUtils authorityUtils, UserDetailsService userDetailsService, CookieManager cookieManager) {
        this.jwtManager = jwtManager;
        this.authorityUtils = authorityUtils;
        this.userDetailsService = userDetailsService;
        this.cookieManager = cookieManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Cookie accessTokenCookie = cookieManager.resolveAccessTokenCookie(request);
            String accessToken = getTokenFromCookie(accessTokenCookie);
            jwtManager.verifyToken(accessToken, jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey()));
            Map<String, Object> claims = getClaims(accessToken);
            setAuthenticationToContext(claims);
        }catch (CustomAuthenticationException | SignatureException ce){
            request.setAttribute("exception", ce);
        } catch (ExpiredJwtException ee) {
            request.setAttribute("exception", ee);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromCookie(Cookie accessTokenCookie) {
        return accessTokenCookie.getValue().replace("Bearer+", "");
    }

    private Claims getClaims(String accessToken) {
        String base64EncodedSecretKey = jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey());
        return jwtManager.getClaims(accessToken, base64EncodedSecretKey).getBody();
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("email");
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List<String>) claims.get("roles"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if(request.getCookies() != null){
            Cookie authorization = cookieManager.resolveAccessTokenCookie(request);
            return authorization == null || !authorization.getValue().startsWith("Bearer");
        }
        return true;
    }
}
