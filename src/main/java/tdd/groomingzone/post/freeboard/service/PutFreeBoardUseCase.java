package tdd.groomingzone.post.freeboard.service;

import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.PutFreeBoardCommand;

public interface PutFreeBoardUseCase {

    SingleFreeBoardCommandResponse putFreeBoard(PutFreeBoardCommand putFreeBoardCommand);
}
