package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command;

import lombok.Data;

@Data
public final class PostFreeBoardCommentCommand {
    private final Long writerId;
    private final Long boardId;
    private final String content;

    private PostFreeBoardCommentCommand(Long writerId, Long boardId, String content) {
        this.writerId = writerId;
        this.boardId = boardId;
        this.content = content;
    }

    public static PostFreeBoardCommentCommand of(long writerId, long boardId, String content) {
        return new PostFreeBoardCommentCommand(writerId, boardId, content);
    }
}
