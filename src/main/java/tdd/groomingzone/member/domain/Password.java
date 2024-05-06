package tdd.groomingzone.member.domain;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import java.util.regex.Pattern;

public record Password(
        String password
) {
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$");

    public Password {
        verifyPassword(password);
    }

    private void verifyPassword(String password) {
        if (!PASSWORD.matcher(password).matches()) {
            throw new BusinessException(ExceptionCode.INVALID_PASSWORD);
        }
    }
}
