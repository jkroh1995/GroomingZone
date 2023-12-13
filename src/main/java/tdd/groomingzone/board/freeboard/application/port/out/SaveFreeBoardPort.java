package tdd.groomingzone.board.freeboard.application.port.out;

public interface SaveFreeBoardPort {

    FreeBoardQueryResult save(SaveFreeBoardQuery query);
}
