package tdd.groomingzone.board.freeboard.application.port.in;

import lombok.Data;
import tdd.groomingzone.board.comment.CommentCommandResponse;
import tdd.groomingzone.board.common.WriterInfo;

import java.time.LocalDateTime;
import java.util.List;

@Data
public final class FreeBoardCommandResponse {
    private final long boardId;
    private final String title;
    private final String content;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final WriterInfo writerInfo;

    private FreeBoardCommandResponse(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerInfo = writerInfo;
    }

    public static FreeBoardCommandResponse of(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        return new FreeBoardCommandResponse(boardId, title, content, viewCount, createdAt, modifiedAt, writerInfo);
    }
}
