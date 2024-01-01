package tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase;

import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.DeleteFreeBoardCommentCommand;

public interface DeleteFreeBoardCommentUseCase {
    void delete(DeleteFreeBoardCommentCommand command);
}
