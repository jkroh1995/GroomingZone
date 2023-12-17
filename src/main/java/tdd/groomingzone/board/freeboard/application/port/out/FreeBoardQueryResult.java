package tdd.groomingzone.board.freeboard.application.port.out;

import lombok.Data;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

@Data
public final class FreeBoardQueryResult {
    private final FreeBoard freeBoard;
    private final long writerId;

    private FreeBoardQueryResult(FreeBoard freeBoard, long writerId) {
        this.freeBoard = freeBoard;
        this.writerId = writerId;
    }

    public static FreeBoardQueryResult of(FreeBoard freeBoard, long writerId){
        return new FreeBoardQueryResult(freeBoard, writerId);
    }
}
