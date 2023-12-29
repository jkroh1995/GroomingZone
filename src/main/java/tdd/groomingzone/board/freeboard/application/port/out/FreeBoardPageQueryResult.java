package tdd.groomingzone.board.freeboard.application.port.out;

import lombok.Data;

import java.util.List;

@Data
public final class FreeBoardPageQueryResult {
    private final List<FreeBoardEntityQueryResult> queryResults;
    private final int pageIndex;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    private FreeBoardPageQueryResult(List<FreeBoardEntityQueryResult> queryResults, int pageIndex, int pageSize, long totalElements, int totalPages) {
        this.queryResults = queryResults;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static FreeBoardPageQueryResult of(List<FreeBoardEntityQueryResult> queryResults, int pageNumber, int pageSize, long totalElements, int totalPages) {
        return new FreeBoardPageQueryResult(queryResults, pageNumber, pageSize, totalElements, totalPages);
    }
}
