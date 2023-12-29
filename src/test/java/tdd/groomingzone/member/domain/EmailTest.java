package tdd.groomingzone.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tdd.groomingzone.global.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    @DisplayName("이메일 형식이 맞지 않으면 예외처리한다.")
    void testCreateEmail() {
        String inappropriateEmail = "nono";
        assertThrows(BusinessException.class, () -> Email.of(inappropriateEmail));
    }
}