package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

public record PostFreeBoardCommentCommand(
        String writerEmail,
        Long boardId,
        String content
)  {
    public static PostFreeBoardCommentCommand of(String writerEmail, Long boardId, String content) {
        return new PostFreeBoardCommentCommand(writerEmail, boardId, content);
    }
}
