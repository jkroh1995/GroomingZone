package tdd.groomingzone.comment.freeboardcomment.application.port.out.port;

import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

public interface SaveFreeBoardCommentPort {

    FreeBoardCommentEntityResult save(FreeBoardComment freeBoardComment);
}
