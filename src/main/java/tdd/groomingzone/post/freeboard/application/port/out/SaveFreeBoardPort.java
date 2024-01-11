package tdd.groomingzone.post.freeboard.application.port.out;

import tdd.groomingzone.post.freeboard.application.port.out.query.SaveFreeBoardQuery;

public interface SaveFreeBoardPort {

    FreeBoardEntityQueryResult save(SaveFreeBoardQuery freeBoard);
}
