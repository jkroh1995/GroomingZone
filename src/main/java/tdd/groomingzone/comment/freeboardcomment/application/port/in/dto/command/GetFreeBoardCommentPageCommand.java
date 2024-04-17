package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

public record GetFreeBoardCommentPageCommand(
        Long boardId,
        int pageNumber
) {
    public static GetFreeBoardCommentPageCommand of(long boardId, int pageNumber) {
        return new GetFreeBoardCommentPageCommand(boardId, pageNumber);
    }
}
