package tdd.groomingzone.comment.common.application.port.out;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class CommentEntityQueryResult {

    private final long commentId;
    private final long writerId;
    private final long boardId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private CommentEntityQueryResult(long commentId, long writerId, long boardId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.writerId = writerId;
        this.boardId = boardId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentEntityQueryResult of(long commentId, long writerId, long boardId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new CommentEntityQueryResult(commentId, writerId,boardId, content, createdAt, modifiedAt);
    }
}
