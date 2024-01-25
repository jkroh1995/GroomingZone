package tdd.groomingzone.post.freeboard.application.port.in.usecase;

import tdd.groomingzone.post.freeboard.application.port.in.MultiFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardPageCommand;

public interface GetFreeBoardPageUseCase {

    MultiFreeBoardCommandResponse getFreeBoardPage(GetFreeBoardPageCommand getFreeBoardPageCommand);
}
