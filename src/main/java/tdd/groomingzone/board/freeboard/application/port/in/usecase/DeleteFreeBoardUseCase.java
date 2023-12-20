package tdd.groomingzone.board.freeboard.application.port.in.usecase;

import tdd.groomingzone.board.freeboard.application.port.in.command.DeleteFreeBoardCommand;

public interface DeleteFreeBoardUseCase {

    void deleteFreeBoard(DeleteFreeBoardCommand command);
}
