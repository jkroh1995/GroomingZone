package tdd.groomingzone.board.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tdd.groomingzone.global.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

class TitleTest {

    @Test
    @DisplayName("제목에 공백이 들어오면 예외처리한다.")
    void testCreateTitle(){
        assertThrows(BusinessException.class, () -> Title.of(""));
    }
}