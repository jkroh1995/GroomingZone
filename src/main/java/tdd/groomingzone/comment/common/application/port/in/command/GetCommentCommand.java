package tdd.groomingzone.comment.common.application.port.in.command;

import lombok.Getter;

@Getter
public final class GetCommentCommand {
    private final long boardId;
    private final int pageNumber;

    private GetCommentCommand(long boardId, int pageNumber) {
        this.boardId = boardId;
        this.pageNumber = pageNumber;
    }

    public static GetCommentCommand of(long boardId, int pageNumber){
        return new GetCommentCommand(boardId, pageNumber);
    }
}
