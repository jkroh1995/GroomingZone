package tdd.groomingzone.comment.freeboardcomment.dto;

public record PutFreeBoardCommentCommand(
        String requestMemberEmail,
        Long boardId,
        Long commentId,
        String content
)  {
    public static PutFreeBoardCommentCommand of(String requestMemberEmail, Long boardId, Long commentId, String content) {
        return new PutFreeBoardCommentCommand(requestMemberEmail, boardId, commentId, content);
    }
}
