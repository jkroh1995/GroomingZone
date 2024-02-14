package tdd.groomingzone.comment.freeboardcomment.adapter.out;

import lombok.Data;
import org.springframework.data.domain.Page;
import tdd.groomingzone.comment.common.CommentEntity;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

import java.util.List;

@Data
public final class FreeBoardCommentPage {
    private final List<FreeBoardComment> freeBoardComments;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    private FreeBoardCommentPage(List<FreeBoardComment> freeBoardComments, Page<CommentEntity> page) {
        this.freeBoardComments = freeBoardComments;
        this.pageNumber = makePageIndexToPageNumber(page.getNumber());
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }

    private int makePageIndexToPageNumber(int page) {
        return page + 1;
    }

    public static FreeBoardCommentPage of(List<FreeBoardComment> freeBoardComments, Page<CommentEntity> page){
        return new FreeBoardCommentPage(freeBoardComments, page);
    }
}
