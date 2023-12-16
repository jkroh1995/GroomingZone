package tdd.groomingzone.board.freeboard.application.port.in;

public interface PutFreeBoardUseCase {

    FreeBoardCommandResponse putFreeBoard(PutFreeBoardCommand putFreeBoardCommand);
}
