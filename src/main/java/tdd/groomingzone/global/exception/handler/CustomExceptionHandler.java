package tdd.groomingzone.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tdd.groomingzone.global.exception.CustomAuthenticationException;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity handleBusinessException(BusinessException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        log.error("# error status : {}, error code : {}", e.getExceptionCode().getStatus(), e.getExceptionCode().getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }

    @ExceptionHandler({CustomAuthenticationException.class})
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(CustomAuthenticationException e){
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());
        log.error("# error status : {}, error code : {}", e.getExceptionCode().getStatus(), e.getExceptionCode().getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }
//
//    @ExceptionHandler
//    public ResponseEntity handleExpiredTokenException(ExpiredJwtException e) {
//        ExceptionCode ec = ExceptionCode.TOKEN_EXPIRED;
//        final ErrorResponse response = ErrorResponse.of(ec);
//        log.error("# error status : {}, error code : {}", ec.getStatus(), ec.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.valueOf(ec.getStatus()));
//    }
}
