package tdd.groomingzone.comment.freeboardcomment.dto;

public record PostFreeBoardCommentCommand(
        String writerEmail,
        Long boardId,
        String content
)  {
    public static PostFreeBoardCommentCommand of(String writerEmail, Long boardId, String content) {
        return new PostFreeBoardCommentCommand(writerEmail, boardId, content);
    }
}
