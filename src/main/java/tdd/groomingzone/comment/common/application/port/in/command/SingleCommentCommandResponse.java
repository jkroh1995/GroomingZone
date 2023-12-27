package tdd.groomingzone.comment.common.application.port.in.command;

import lombok.Getter;
import tdd.groomingzone.board.common.WriterInfo;

import java.time.LocalDateTime;

@Getter
public final class SingleCommentCommandResponse {
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;;
    private final WriterInfo writerInfo;

    private SingleCommentCommandResponse(String content, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerInfo = writerInfo;
    }

    public static SingleCommentCommandResponse of(String content, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo){
        return new SingleCommentCommandResponse(content, createdAt, modifiedAt, writerInfo);
    }
}
