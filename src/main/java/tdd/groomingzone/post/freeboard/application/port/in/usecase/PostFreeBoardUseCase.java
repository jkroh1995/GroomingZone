package tdd.groomingzone.post.freeboard.application.port.in.usecase;

import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.PostFreeBoardCommand;

public interface PostFreeBoardUseCase {

    SingleFreeBoardCommandResponse postFreeBoard(PostFreeBoardCommand command);
}
