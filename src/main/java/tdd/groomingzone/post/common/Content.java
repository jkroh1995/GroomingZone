package tdd.groomingzone.post.common;

import lombok.Data;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

@Data
public final class Content {
    private final String content;

    private Content(String content) {
        verifyContent(content);
        this.content = content;
    }

    public static Content of(String content){
        return new Content(content);
    }

    private void verifyContent(String content) {
        if(content.isBlank()){
            throw new BusinessException(ExceptionCode.INVALID_BOARD_CONTENT);
        }
    }
}
