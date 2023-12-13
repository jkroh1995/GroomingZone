package tdd.groomingzone.board.freeboard.application.port.in;

import lombok.Getter;
import tdd.groomingzone.board.common.WriterInfo;

import java.time.LocalDateTime;

@Getter
public final class PostFreeBoardResponse {
    private final long boardId;
    private final String title;
    private final String content;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final WriterInfo writerInfo;

    private PostFreeBoardResponse(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerInfo = writerInfo;
    }

    public static PostFreeBoardResponse of(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        return new PostFreeBoardResponse(boardId, title, content, viewCount, createdAt, modifiedAt, writerInfo);
    }
}
