package tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase;

import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PostFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;

public interface PostFreeBoardCommentUseCase {
    SingleFreeBoardCommentResponse postFreeBoardComment(PostFreeBoardCommentCommand postFreeBoardCommentCommand);
}
