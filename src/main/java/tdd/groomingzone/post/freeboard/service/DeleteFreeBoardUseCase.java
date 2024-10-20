package tdd.groomingzone.post.freeboard.service;

import tdd.groomingzone.post.freeboard.dto.DeleteFreeBoardCommand;

public interface DeleteFreeBoardUseCase {

    void deleteFreeBoard(DeleteFreeBoardCommand command);
}
