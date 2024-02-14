package tdd.groomingzone.post.freeboard.application.port.out;

import tdd.groomingzone.post.freeboard.domain.FreeBoard;

public interface SaveFreeBoardPort {

    FreeBoard save(FreeBoard freeBoard);
}
