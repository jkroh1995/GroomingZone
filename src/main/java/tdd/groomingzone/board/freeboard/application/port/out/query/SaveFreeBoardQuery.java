package tdd.groomingzone.board.freeboard.application.port.out.query;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class SaveFreeBoardQuery {
    private final long writerId;
    private final String writerNickName;
    private final long boardId;
    private final String title;
    private final String content;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private SaveFreeBoardQuery(long writerId, String writerNickName, long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.writerId = writerId;
        this.writerNickName = writerNickName;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static SaveFreeBoardQuery of(long writerId, String writerNickName, long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new SaveFreeBoardQuery(writerId, writerNickName, boardId, title, content, viewCount, createdAt, modifiedAt);
    }
}
