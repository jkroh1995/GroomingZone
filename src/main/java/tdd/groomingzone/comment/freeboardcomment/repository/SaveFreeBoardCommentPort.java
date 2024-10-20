package tdd.groomingzone.comment.freeboardcomment.repository;

import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;

public interface SaveFreeBoardCommentPort {

    FreeBoardComment save(FreeBoardComment freeBoardComment);
}
