package tdd.groomingzone.board.freeboard.application.port.in;

public interface PostFreeBoardUseCase {

    PostFreeBoardResponse postFreeBoard(PostFreeBoardCommand command);
}
