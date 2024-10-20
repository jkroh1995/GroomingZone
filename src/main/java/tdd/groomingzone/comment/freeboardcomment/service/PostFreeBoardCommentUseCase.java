package tdd.groomingzone.comment.freeboardcomment.service;

import tdd.groomingzone.comment.freeboardcomment.dto.PostFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.dto.SingleFreeBoardCommentResponse;

public interface PostFreeBoardCommentUseCase {
    SingleFreeBoardCommentResponse postFreeBoardComment(PostFreeBoardCommentCommand postFreeBoardCommentCommand);
}
