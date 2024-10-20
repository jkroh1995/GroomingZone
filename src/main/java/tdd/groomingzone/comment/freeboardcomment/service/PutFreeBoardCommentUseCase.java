package tdd.groomingzone.comment.freeboardcomment.service;

import tdd.groomingzone.comment.freeboardcomment.dto.PutFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.dto.SingleFreeBoardCommentResponse;

public interface PutFreeBoardCommentUseCase {
    SingleFreeBoardCommentResponse putFreeBoard(PutFreeBoardCommentCommand command);
}
