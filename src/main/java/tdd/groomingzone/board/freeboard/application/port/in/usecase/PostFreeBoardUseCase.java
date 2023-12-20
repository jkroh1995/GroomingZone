package tdd.groomingzone.board.freeboard.application.port.in.usecase;

import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.PostFreeBoardCommand;

public interface PostFreeBoardUseCase {

    FreeBoardEntityCommandResponse postFreeBoard(PostFreeBoardCommand command);
}
