package tdd.groomingzone.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import tdd.groomingzone.auth.RedisService;
import tdd.groomingzone.auth.utils.JwtManager;
import tdd.groomingzone.auth.service.MemberDetailsService;
import tdd.groomingzone.auth.utils.CustomAuthorityUtils;
import tdd.groomingzone.global.exception.CustomAuthenticationException;
import tdd.groomingzone.global.exception.ExceptionCode;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final CustomAuthorityUtils authorityUtils;
    private final RedisService redisService;
    private final MemberDetailsService memberDetailsService;

    public JwtVerificationFilter(JwtManager jwtManager,CustomAuthorityUtils authorityUtils, RedisService redisService, MemberDetailsService memberDetailsService) {
        this.jwtManager = jwtManager;
        this.authorityUtils = authorityUtils;
        this.redisService = redisService;
        this.memberDetailsService = memberDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtManager.getTokenFromHeader(request.getHeader("Authorization"));
        try {
            if (redisService.isSignOut(accessToken)) {
                throw new CustomAuthenticationException(ExceptionCode.NOT_SIGN_IN);
            }
            jwtManager.verifyToken(accessToken, jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey()));
            Map<String, Object> claims = getClaims(request);
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

    private Claims getClaims(HttpServletRequest request) {
        String jws = jwtManager.getTokenFromHeader(request.getHeader("Authorization"));
        String base64EncodedSecretKey = jwtManager.encodeBase64SecretKey(jwtManager.getSecretKey());
        return jwtManager.getClaims(jws, base64EncodedSecretKey).getBody();
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("email");
        UserDetails memberDetails = memberDetailsService.loadUserByUsername(username);
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List<String>) claims.get("roles"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer");
    }
}
