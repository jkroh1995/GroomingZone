package tdd.groomingzone.comment.common.application.port.out;

import lombok.Getter;

import java.util.List;

@Getter
public final class CommentPageQueryResult {
    private final List<CommentEntityQueryResult> queryResults;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    private CommentPageQueryResult(List<CommentEntityQueryResult> queryResults, int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.queryResults = queryResults;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static CommentPageQueryResult of(List<CommentEntityQueryResult> queryResults, int pageNumber, int pageSize, long totalElements, int totalPages) {
        return new CommentPageQueryResult(queryResults, pageNumber, pageSize, totalElements, totalPages);
    }
}
