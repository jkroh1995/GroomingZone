package tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase;

import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.GetFreeBoardCommentPageCommand;

public interface GetFreeBoardCommentUseCase {
    MultiFreeBoardCommentResponse getFreeBoardComment(GetFreeBoardCommentPageCommand command);
}
