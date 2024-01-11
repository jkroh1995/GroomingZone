package tdd.groomingzone.post.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tdd.groomingzone.global.exception.BusinessException;

import static org.junit.jupiter.api.Assertions.*;

class ContentTest {

    @Test
    @DisplayName("글 내용에 빈칸이 들어오면 예외처리한다.")
    void testCreateContent(){
        assertThrows(BusinessException.class, () -> Content.of(""));
    }
}