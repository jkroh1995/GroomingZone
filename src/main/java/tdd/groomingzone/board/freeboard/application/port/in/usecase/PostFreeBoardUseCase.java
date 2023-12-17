package tdd.groomingzone.board.freeboard.application.port.in.usecase;

import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.PostFreeBoardCommand;

public interface PostFreeBoardUseCase {

    FreeBoardCommandResponse postFreeBoard(PostFreeBoardCommand command);
}
