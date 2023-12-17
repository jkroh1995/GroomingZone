package tdd.groomingzone.member.domain;

import lombok.Getter;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.regex.Pattern;

@Getter
public final class Password {
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$");
    private final String password;

    private Password(String password) {
        verifyPassword(password);
        this.password = password;
    }

    public static Password of(String password){
        return new Password(password);
    }

    private void verifyPassword(String password) {
        if(!PASSWORD.matcher(password).matches()){
            throw new BusinessException(ExceptionCode.INVALID_PASSWORD);
        }
    }
}
