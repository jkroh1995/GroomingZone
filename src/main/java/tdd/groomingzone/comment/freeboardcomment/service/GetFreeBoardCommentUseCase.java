package tdd.groomingzone.comment.freeboardcomment.service;

import tdd.groomingzone.comment.freeboardcomment.dto.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.dto.GetFreeBoardCommentPageCommand;

public interface GetFreeBoardCommentUseCase {
    MultiFreeBoardCommentResponse getFreeBoardComment(GetFreeBoardCommentPageCommand command);
}
