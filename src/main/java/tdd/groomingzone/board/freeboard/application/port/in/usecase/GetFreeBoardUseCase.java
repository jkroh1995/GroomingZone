package tdd.groomingzone.board.freeboard.application.port.in.usecase;

import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.GetFreeBoardCommand;

public interface GetFreeBoardUseCase {
    FreeBoardEntityCommandResponse getFreeBoard(GetFreeBoardCommand getFreeBoardCommand);
}
