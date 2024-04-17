package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

public record DeleteFreeBoardCommentCommand(
        String requestMemberEmail,
        Long freeBoardId,
        Long commentId

) {
    public static DeleteFreeBoardCommentCommand of(String requestMemberEmail, long freeBoardId, long commentId) {
        return new DeleteFreeBoardCommentCommand(requestMemberEmail, freeBoardId, commentId);
    }
}
