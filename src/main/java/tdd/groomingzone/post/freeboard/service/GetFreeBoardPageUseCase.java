package tdd.groomingzone.post.freeboard.service;

import tdd.groomingzone.post.freeboard.dto.MultiFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.GetFreeBoardPageCommand;

public interface GetFreeBoardPageUseCase {

    MultiFreeBoardCommandResponse getFreeBoardPage(GetFreeBoardPageCommand getFreeBoardPageCommand);
}
