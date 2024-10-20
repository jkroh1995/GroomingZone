package tdd.groomingzone.post.freeboard.service;

import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.GetFreeBoardCommand;

public interface GetFreeBoardUseCase {
    SingleFreeBoardCommandResponse getFreeBoard(GetFreeBoardCommand getFreeBoardCommand);
}
