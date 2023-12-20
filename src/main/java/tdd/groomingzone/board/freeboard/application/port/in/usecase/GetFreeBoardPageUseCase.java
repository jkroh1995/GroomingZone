package tdd.groomingzone.board.freeboard.application.port.in.usecase;

import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardPageCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.GetFreeBoardPageCommand;

public interface GetFreeBoardPageUseCase {

    FreeBoardPageCommandResponse getFreeBoardPage(GetFreeBoardPageCommand getFreeBoardPageCommand);
}
