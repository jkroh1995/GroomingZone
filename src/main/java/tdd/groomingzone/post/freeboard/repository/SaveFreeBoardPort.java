package tdd.groomingzone.post.freeboard.repository;

import tdd.groomingzone.post.freeboard.domain.FreeBoard;

public interface SaveFreeBoardPort {

    FreeBoard save(FreeBoard freeBoard);
}
