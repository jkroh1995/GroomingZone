package tdd.groomingzone.auth.utils.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import tdd.groomingzone.auth.utils.ErrorResponder;
import tdd.groomingzone.global.exception.ExceptionCodeMapper;
import tdd.groomingzone.global.exception.ExceptionCode;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("# 로그인 실패: {}", exception.getMessage());
        ExceptionCode exceptionCode = ExceptionCodeMapper.mapExceptionCode(exception.getMessage());
        ErrorResponder.sendErrorResponse(response, exceptionCode);
    }
}
