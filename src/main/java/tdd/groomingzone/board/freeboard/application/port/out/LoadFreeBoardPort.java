package tdd.groomingzone.board.freeboard.application.port.out;

public interface LoadFreeBoardPort {

    FreeBoardQueryResult loadFreeBoardById(long freeBoardId);
}
