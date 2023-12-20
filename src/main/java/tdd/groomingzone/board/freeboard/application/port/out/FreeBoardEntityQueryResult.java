package tdd.groomingzone.board.freeboard.application.port.out;

import lombok.Data;
import tdd.groomingzone.board.freeboard.domain.FreeBoard;

@Data
public final class FreeBoardEntityQueryResult {
    private final FreeBoard freeBoard;
    private final long writerId;

    private FreeBoardEntityQueryResult(FreeBoard freeBoard, long writerId) {
        this.freeBoard = freeBoard;
        this.writerId = writerId;
    }

    public static FreeBoardEntityQueryResult of(FreeBoard freeBoard, long writerId){
        return new FreeBoardEntityQueryResult(freeBoard, writerId);
    }
}
