package tdd.groomingzone.board.freeboard.application.port.in;

public interface PostFreeBoardUseCase {

    FreeBoardCommandResponse postFreeBoard(PostFreeBoardCommand command);
}
