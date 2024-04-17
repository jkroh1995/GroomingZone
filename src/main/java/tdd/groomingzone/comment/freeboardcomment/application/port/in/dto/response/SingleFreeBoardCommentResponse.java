package tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response;

import tdd.groomingzone.post.common.WriterInfo;

import java.time.LocalDateTime;

public record SingleFreeBoardCommentResponse(
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        WriterInfo writerInfo
)  {
    public static SingleFreeBoardCommentResponse of(String content, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo){
        return new SingleFreeBoardCommentResponse(content, createdAt, modifiedAt, writerInfo);
    }
}
