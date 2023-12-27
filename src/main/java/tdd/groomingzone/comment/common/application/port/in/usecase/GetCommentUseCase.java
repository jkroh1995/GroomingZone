package tdd.groomingzone.comment.common.application.port.in.usecase;

import tdd.groomingzone.comment.common.application.port.in.command.MultiCommentCommandResponse;
import tdd.groomingzone.comment.common.application.port.in.command.GetCommentCommand;

public interface GetCommentUseCase {
    MultiCommentCommandResponse getFreeBoardComment(GetCommentCommand command);
}
