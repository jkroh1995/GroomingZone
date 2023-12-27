package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response;

import lombok.Getter;
import tdd.groomingzone.board.common.WriterInfo;

import java.time.LocalDateTime;

@Getter
public final class SingleFreeBoardCommentResponse {
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final WriterInfo writerInfo;

    private SingleFreeBoardCommentResponse(String content, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerInfo = writerInfo;
    }

    public static SingleFreeBoardCommentResponse of(String content, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo){
        return new SingleFreeBoardCommentResponse(content, createdAt, modifiedAt, writerInfo);
    }
}
