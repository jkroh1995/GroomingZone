package tdd.groomingzone.member.domain;

import lombok.Getter;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.regex.Pattern;

@Getter
public final class Email {
    private static final Pattern EMAIL = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
    private final String email;

    private Email(String email) {
        verifyEmail(email);
        this.email = email;
    }

    public static Email of(String email){
        return new Email(email);
    }

    private void verifyEmail(String email) {
        if(!EMAIL.matcher(email).matches()){
            throw new BusinessException(ExceptionCode.INVALID_EMAIL);
        }
    }
}
