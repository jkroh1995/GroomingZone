package tdd.groomingzone.board.freeboard.application.port.out;

import tdd.groomingzone.board.freeboard.domain.FreeBoard;

public interface SaveFreeBoardPort {

    SingleFreeBoardQueryResult save(FreeBoard freeBoard);
}
