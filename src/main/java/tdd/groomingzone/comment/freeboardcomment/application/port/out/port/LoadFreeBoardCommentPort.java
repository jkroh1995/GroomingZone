package tdd.groomingzone.comment.freeboardcomment.application.port.out.port;

import tdd.groomingzone.comment.freeboardcomment.adapter.out.FreeBoardCommentPage;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageable;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

public interface LoadFreeBoardCommentPort {

    FreeBoardCommentPage loadFreeBoardCommentPage(FreeBoardCommentPageable freeBoardCommentPageable);

    FreeBoardComment loadFreeBoardComment(long commentId);
}
