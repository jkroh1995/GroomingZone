package tdd.groomingzone.board.freeboard.application.port.out.query;

import lombok.Getter;

@Getter
public class DeleteFreeBoardQuery {

    private final long boardId;

    private DeleteFreeBoardQuery(long boardId) {
        this.boardId = boardId;
    }

    public static DeleteFreeBoardQuery of(long boardId) {
        return new DeleteFreeBoardQuery(boardId);
    }
}
