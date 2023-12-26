package tdd.groomingzone.comment.freeboardcomment.application.port.out;

import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

public interface SaveFreeBoardCommentPort {

    void save(FreeBoardComment freeBoardComment);
}
