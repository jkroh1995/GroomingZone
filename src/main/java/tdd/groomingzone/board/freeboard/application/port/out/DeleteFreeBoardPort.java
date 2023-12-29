package tdd.groomingzone.board.freeboard.application.port.out;

import tdd.groomingzone.board.freeboard.application.port.out.query.DeleteFreeBoardQuery;

public interface DeleteFreeBoardPort {

    void delete(DeleteFreeBoardQuery query);
}
