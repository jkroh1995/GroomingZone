package tdd.groomingzone.comment.freeboardcomment.application.port.out;

import lombok.Getter;

import java.util.List;

@Getter
public final class FreeBoardCommentPageResult {
    private final List<FreeBoardCommentEntityResult> queryResults;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    private FreeBoardCommentPageResult(List<FreeBoardCommentEntityResult> queryResults, int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.queryResults = queryResults;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static FreeBoardCommentPageResult of(List<FreeBoardCommentEntityResult> queryResults, int pageNumber, int pageSize, long totalElements, int totalPages) {
        return new FreeBoardCommentPageResult(queryResults, pageNumber, pageSize, totalElements, totalPages);
    }
}
