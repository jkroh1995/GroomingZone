package tdd.groomingzone.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tdd.groomingzone.global.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    @DisplayName("비밀번호가 적절하지 않으면 예외처리한다.")
    void testInappropriatePasswordThrowException() {
        String inappropriatePassword = "nono";
        assertThrows(BusinessException.class, () -> new Password(inappropriatePassword));
    }
}