package tdd.groomingzone.board.freeboard.application.port.in;

import lombok.Data;
import tdd.groomingzone.board.common.WriterInfo;

import java.time.LocalDateTime;

@Data
public final class SingleFreeBoardCommandResponse {
    private final long boardId;
    private final String title;
    private final String content;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final WriterInfo writerInfo;

    private SingleFreeBoardCommandResponse(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerInfo = writerInfo;
    }

    public static SingleFreeBoardCommandResponse of(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        return new SingleFreeBoardCommandResponse(boardId, title, content, viewCount, createdAt, modifiedAt, writerInfo);
    }
}
