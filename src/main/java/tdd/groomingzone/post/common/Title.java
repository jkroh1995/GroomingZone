package tdd.groomingzone.post.common;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

public record Title(
        String title
) {
    public Title {
        verifyTitle(title);
    }

    private void verifyTitle(String title) {
        if (title.isBlank()) {
            throw new BusinessException(ExceptionCode.INVALID_BOARD_TITLE);
        }
    }
}
