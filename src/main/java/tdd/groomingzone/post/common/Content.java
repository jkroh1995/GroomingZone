package tdd.groomingzone.post.common;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

public record Content(
        String content
) {
    public Content {
        verifyContent(content);
    }

    private void verifyContent(String content) {
        if (content.isBlank()) {
            throw new BusinessException(ExceptionCode.INVALID_BOARD_CONTENT);
        }
    }
}
