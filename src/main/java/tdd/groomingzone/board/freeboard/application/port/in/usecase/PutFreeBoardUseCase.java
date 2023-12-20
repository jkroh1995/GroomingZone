package tdd.groomingzone.board.freeboard.application.port.in.usecase;

import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.PutFreeBoardCommand;

public interface PutFreeBoardUseCase {

    FreeBoardEntityCommandResponse putFreeBoard(PutFreeBoardCommand putFreeBoardCommand);
}
