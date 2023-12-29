package tdd.groomingzone.board.freeboard.application.port.out;

import tdd.groomingzone.board.freeboard.application.port.out.query.SaveFreeBoardQuery;

public interface SaveFreeBoardPort {

    FreeBoardEntityQueryResult save(SaveFreeBoardQuery freeBoard);
}
