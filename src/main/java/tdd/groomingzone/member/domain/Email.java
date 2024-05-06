package tdd.groomingzone.member.domain;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.regex.Pattern;

public record Email(
        String email
) {
    private static final Pattern EMAIL = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");

    public Email {
        verifyEmail(email);
    }

    private void verifyEmail(String email) {
        if(!EMAIL.matcher(email).matches()){
            throw new BusinessException(ExceptionCode.INVALID_EMAIL);
        }
    }
}
