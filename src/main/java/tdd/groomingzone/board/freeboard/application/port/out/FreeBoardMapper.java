package tdd.groomingzone.board.freeboard.application.port.out;

import tdd.groomingzone.board.freeboard.adapter.out.persistence.FreeBoardEntity;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

public interface FreeBoardMapper {
    FreeBoardEntity mapToDatabaseEntity(FreeBoard freeBoard);

    FreeBoard mapToDomainEntity(FreeBoard freeBoard, FreeBoardEntity savedFreeBoardEntity);
}
