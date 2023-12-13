package tdd.groomingzone.board.freeboard.application.port.in;

public interface PostFreeBoardUseCase {

    PostFreeBoardResult postFreeBoard(PostFreeBoardCommand command);
}
