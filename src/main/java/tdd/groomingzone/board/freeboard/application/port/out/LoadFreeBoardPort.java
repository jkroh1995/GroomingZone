package tdd.groomingzone.board.freeboard.application.port.out;

public interface LoadFreeBoardPort {

    SingleFreeBoardQueryResult loadFreeBoardById(long freeBoardId);
}
