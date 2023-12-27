package tdd.groomingzone.comment.common.application.port.out;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;
import tdd.groomingzone.global.utils.CommonEnums;

import static tdd.groomingzone.global.utils.CommonEnums.PAGE_SIZE;

@Getter
public final class CommentPage {
    private final long boardId;
    private final Pageable pageable;

    public CommentPage(long boardId, int pageNumber) {
        verifyPageNumber(pageNumber);
        this.boardId = boardId;
        this.pageable = Pageable.ofSize(PAGE_SIZE.getValue()).withPage(pageNumber - 1);
    }

    private void verifyPageNumber(int pageNumber) {
        if(pageNumber < CommonEnums.MINIMUM_PAGE_NUMBER_VALUE.getValue()){
            throw new BusinessException(ExceptionCode.INVALID_REQUEST);
        }
    }
}
