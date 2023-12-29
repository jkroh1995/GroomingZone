package tdd.groomingzone.comment.freeboardcomment.application.port.out.port;

import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageable;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentPageResult;

public interface LoadFreeBoardCommentPort {

    FreeBoardCommentPageResult loadFreeBoardCommentPage(FreeBoardCommentPageable freeBoardCommentPageable);
}
