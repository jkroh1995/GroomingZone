package tdd.groomingzone.post.recruitment.application.port.in;

import lombok.Getter;
import tdd.groomingzone.post.common.WriterInfo;

import java.time.LocalDateTime;

@Getter
public final class SingleRecruitmentResponse {
    private final long boardId;
    private final String title;
    private final String content;
    private final String type;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final WriterInfo writerInfo;

    private SingleRecruitmentResponse(long boardId, String title, String content, String type, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.type = type;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writerInfo = writerInfo;
    }

    public static SingleRecruitmentResponse of(long boardId, String title, String content, String type, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, WriterInfo writerInfo){
        return new SingleRecruitmentResponse(boardId, title, content, type, viewCount, createdAt, modifiedAt, writerInfo);
    }
}
