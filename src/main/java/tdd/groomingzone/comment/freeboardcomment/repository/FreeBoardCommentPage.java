package tdd.groomingzone.comment.freeboardcomment.repository;

import org.springframework.data.domain.Page;
import tdd.groomingzone.comment.common.CommentEntity;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

import java.util.List;

public record FreeBoardCommentPage(
        List<FreeBoardComment> freeBoardComments,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
    public static FreeBoardCommentPage of(List<FreeBoardComment> freeBoardComments, Page<CommentEntity> page){
        return new FreeBoardCommentPage(freeBoardComments, page.getNumber() + 1, page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
