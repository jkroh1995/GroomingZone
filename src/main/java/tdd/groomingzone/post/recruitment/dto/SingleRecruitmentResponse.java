package tdd.groomingzone.post.recruitment.dto;

import lombok.Getter;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.recruitment.domain.Recruitment;

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

    public static SingleRecruitmentResponse of(Recruitment recruitment){
        return new SingleRecruitmentResponse(recruitment.getBoardId(),
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.getRecruitmentType(),
                recruitment.getViewCount(),
                recruitment.getCreatedAt(),
                recruitment.getModifiedAt(),
                WriterInfo.of(recruitment.getWriterId(), recruitment.getWriterNickName()));
    }
}
