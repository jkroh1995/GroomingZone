package tdd.groomingzone.board.freeboard.application.port.out;

import lombok.Data;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

@Data
public final class SingleFreeBoardQueryResult {
    private final FreeBoard freeBoard;
    private final long writerId;

    private SingleFreeBoardQueryResult(FreeBoard freeBoard, long writerId) {
        this.freeBoard = freeBoard;
        this.writerId = writerId;
    }

    public static SingleFreeBoardQueryResult of(FreeBoard freeBoard, long writerId){
        return new SingleFreeBoardQueryResult(freeBoard, writerId);
    }
}
