package tdd.groomingzone.global.pagedresponse;

import lombok.Getter;

@Getter
public final class PageInfo {
    private final int pageNumber;
    private final int size;
    private final long totalElements;
    private final int totalPage;

    private PageInfo(int pageNumber, int size, long totalElements, int totalPage) {
        this.pageNumber = pageNumber;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPage = totalPage;
    }

    public static PageInfo of(int page, int size, long totalElements, int totalPage){
        return new PageInfo(page, size, totalElements, totalPage);
    }
}