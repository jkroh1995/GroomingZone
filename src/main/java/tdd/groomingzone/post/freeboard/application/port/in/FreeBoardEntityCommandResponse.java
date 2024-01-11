package tdd.groomingzone.post.freeboard.application.port.in;

import lombok.Data;
import tdd.groomingzone.post.common.WriterInfo;

import java.time.LocalDateTime;

@Data
public final class FreeBoardEntityCommandResponse {
    private final long boardId;
    private final String title;
    private final String content;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final WriterInfo writerInfo;

    private FreeBoardEntityCommandResponse(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerInfo = writerInfo;
    }

    public static FreeBoardEntityCommandResponse of(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        return new FreeBoardEntityCommandResponse(boardId, title, content, viewCount, createdAt, modifiedAt, writerInfo);
    }
}
