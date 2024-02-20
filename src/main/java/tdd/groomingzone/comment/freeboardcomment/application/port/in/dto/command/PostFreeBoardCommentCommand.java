package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

import lombok.Data;

@Data
public final class PostFreeBoardCommentCommand {
    private final String writerEmail;
    private final Long boardId;
    private final String content;

    private PostFreeBoardCommentCommand(String writerEmail, Long boardId, String content) {
        this.writerEmail = writerEmail;
        this.boardId = boardId;
        this.content = content;
    }

    public static PostFreeBoardCommentCommand of(String writerEmail, Long boardId, String content) {
        return new PostFreeBoardCommentCommand(writerEmail, boardId, content);
    }
}
