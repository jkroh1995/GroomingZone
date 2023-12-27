package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

import lombok.Getter;

@Getter
public final class GetFreeBoardCommentPageCommand {
    private final long boardId;
    private final int pageNumber;

    private GetFreeBoardCommentPageCommand(long boardId, int pageNumber) {
        this.boardId = boardId;
        this.pageNumber = pageNumber;
    }

    public static GetFreeBoardCommentPageCommand of(long boardId, int pageNumber){
        return new GetFreeBoardCommentPageCommand(boardId, pageNumber);
    }
}
