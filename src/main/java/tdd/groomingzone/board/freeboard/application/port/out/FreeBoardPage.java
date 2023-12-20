package tdd.groomingzone.board.freeboard.application.port.out;

import lombok.Getter;

import org.springframework.data.domain.Pageable;
import tdd.groomingzone.board.utils.BoardEnums;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import static tdd.groomingzone.board.utils.BoardEnums.PAGE_SIZE;

@Getter
public class FreeBoardPage {
    private final Pageable pageable;

    public FreeBoardPage(int pageNumber) {
        verifyPageNumber(pageNumber);
        this.pageable = Pageable.ofSize(PAGE_SIZE.getValue()).withPage(pageNumber - 1);
    }

    private void verifyPageNumber(int pageNumber) {
        if(pageNumber < BoardEnums.MINIMUM_PAGE_NUMBER_VALUE.getValue()){
            throw new BusinessException(ExceptionCode.INVALID_REQUEST);
        }
    }
}
