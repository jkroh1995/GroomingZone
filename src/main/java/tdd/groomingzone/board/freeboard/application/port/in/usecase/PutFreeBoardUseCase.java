package tdd.groomingzone.board.freeboard.application.port.in.usecase;

import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.PutFreeBoardCommand;

public interface PutFreeBoardUseCase {

    FreeBoardCommandResponse putFreeBoard(PutFreeBoardCommand putFreeBoardCommand);
}
