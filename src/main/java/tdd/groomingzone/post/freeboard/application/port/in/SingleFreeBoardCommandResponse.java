package tdd.groomingzone.post.freeboard.application.port.in;

import tdd.groomingzone.post.common.WriterInfo;

import java.time.LocalDateTime;

public record SingleFreeBoardCommandResponse(
        Long boardId,
        String title,
        String content,
        int viewCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        WriterInfo writerInfo
) {
    public static SingleFreeBoardCommandResponse of(long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        return new SingleFreeBoardCommandResponse(boardId, title, content, viewCount, createdAt, modifiedAt, writerInfo);
    }
}
