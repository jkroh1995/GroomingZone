package tdd.groomingzone.post.freeboard.application.port.in.usecase;

import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardCommand;

public interface GetFreeBoardUseCase {
    SingleFreeBoardCommandResponse getFreeBoard(GetFreeBoardCommand getFreeBoardCommand);
}
