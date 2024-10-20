package tdd.groomingzone.comment.freeboardcomment.service;

import tdd.groomingzone.comment.freeboardcomment.dto.DeleteFreeBoardCommentCommand;

public interface DeleteFreeBoardCommentUseCase {
    void delete(DeleteFreeBoardCommentCommand command);
}
