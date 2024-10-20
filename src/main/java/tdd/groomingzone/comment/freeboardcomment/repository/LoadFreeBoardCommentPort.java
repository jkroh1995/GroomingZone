package tdd.groomingzone.comment.freeboardcomment.repository;

import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

public interface LoadFreeBoardCommentPort {

    FreeBoardCommentPage loadFreeBoardCommentPage(FreeBoardCommentPageable freeBoardCommentPageable);

    FreeBoardComment loadFreeBoardComment(long commentId);
}
