package tdd.groomingzone.post.freeboard.application.port.in.usecase;

import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.PutFreeBoardCommand;

public interface PutFreeBoardUseCase {

    SingleFreeBoardCommandResponse putFreeBoard(PutFreeBoardCommand putFreeBoardCommand);
}
