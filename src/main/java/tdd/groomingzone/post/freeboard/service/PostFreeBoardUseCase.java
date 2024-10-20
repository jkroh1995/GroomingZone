package tdd.groomingzone.post.freeboard.service;

import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.PostFreeBoardCommand;

public interface PostFreeBoardUseCase {

    SingleFreeBoardCommandResponse postFreeBoard(PostFreeBoardCommand command);
}
