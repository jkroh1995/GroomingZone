package tdd.groomingzone.board.freeboard.application.port.in.usecase;

import tdd.groomingzone.board.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.command.PostFreeBoardCommand;

public interface PostFreeBoardUseCase {

    SingleFreeBoardCommandResponse postFreeBoard(PostFreeBoardCommand command);
}
