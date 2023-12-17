package tdd.groomingzone.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import tdd.groomingzone.auth.utils.ErrorResponder;
import tdd.groomingzone.global.exception.ExceptionCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("# 로그인 실패: {}", exception.getMessage());
        ErrorResponder.sendErrorResponse(response, ExceptionCode.INVALID_SIGN_IN);
    }
}
