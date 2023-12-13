package tdd.groomingzone.board.freeboard.application.port.out;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class FreeBoardQueryResult {
    private final long writerId;
    private final long boardId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final int viewCount;

    private FreeBoardQueryResult(long writerId, long boardId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, int viewCount) {
        this.writerId = writerId;
        this. boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.viewCount = viewCount;
    }

    public static FreeBoardQueryResult of(long writerId, long boardId, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, int viewCount) {
        return new FreeBoardQueryResult(writerId, boardId, title, content, createdAt, modifiedAt, viewCount);
    }
}
