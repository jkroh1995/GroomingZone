package tdd.groomingzone.board.freeboard.application.port.in;

import tdd.groomingzone.member.entity.Member;

public interface PostFreeBoardUseCase {

    PostFreeBoardResult postFreeBoard(PostFreeBoardCommand command);
}
