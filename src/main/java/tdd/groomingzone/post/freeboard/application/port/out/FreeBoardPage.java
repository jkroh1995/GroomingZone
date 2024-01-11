package tdd.groomingzone.post.freeboard.application.port.out;

import lombok.Getter;

import org.springframework.data.domain.Pageable;
import tdd.groomingzone.global.utils.CommonEnums;
import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

import static tdd.groomingzone.global.utils.CommonEnums.PAGE_SIZE;

@Getter
public class FreeBoardPage {
    private final Pageable pageable;

    public FreeBoardPage(int pageNumber) {
        verifyPageNumber(pageNumber);
        this.pageable = Pageable.ofSize(PAGE_SIZE.getValue()).withPage(pageNumber - 1);
    }

    private void verifyPageNumber(int pageNumber) {
        if(pageNumber < CommonEnums.MINIMUM_PAGE_NUMBER_VALUE.getValue()){
            throw new BusinessException(ExceptionCode.INVALID_REQUEST);
        }
    }
}
