package tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase;

import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PostFreeBoardCommentCommand;

public interface PostFreeBoardCommentUseCase {
    void postFreeBoardComment(PostFreeBoardCommentCommand postFreeBoardCommentCommand);
}
