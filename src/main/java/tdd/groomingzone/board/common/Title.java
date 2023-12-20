package tdd.groomingzone.board.common;

import lombok.Getter;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Getter
public final class Title {
    private final String title;

    private Title(String title) {
        verifyTitle(title);
        this.title = title;
    }

    public static Title of(String title){
        return new Title(title);
    }

    private void verifyTitle(String title) {
        if(title.isBlank()){
            throw new BusinessException(ExceptionCode.INVALID_BOARD_TITLE);
        }
    }
}
