package tdd.groomingzone.post.common;

import tdd.groomingzone.global.exception.BusinessException;
import tdd.groomingzone.global.exception.ExceptionCode;

public record PageNumber(int pageNumber) {
    private static final int MINIMUM_PAGE_NUMBER = 1;

    public PageNumber(int pageNumber) {
        validatePageNumber(pageNumber);
        this.pageNumber = getPageIndex(pageNumber);
    }

    private static int getPageIndex(int pageNumber) {
        return pageNumber - 1;
    }

    private void validatePageNumber(int pageNumber) {
        if (pageNumber < MINIMUM_PAGE_NUMBER) {
            throw new BusinessException(ExceptionCode.INVALID_PAGE_NUMBER);
        }
    }
}

