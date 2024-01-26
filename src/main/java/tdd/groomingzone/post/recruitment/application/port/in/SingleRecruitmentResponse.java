package tdd.groomingzone.post.recruitment.application.port.in;

import lombok.Getter;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.recruitment.application.port.out.RecruitmentEntityQueryResult;

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

    public static SingleRecruitmentResponse of(RecruitmentEntityQueryResult queryResult){
        return new SingleRecruitmentResponse(queryResult.getBoardId(),
                queryResult.getTitle(),
                queryResult.getContent(),
                queryResult.getType(),
                queryResult.getViewCount(),
                queryResult.getCreatedAt(),
                queryResult.getModifiedAt(),
                WriterInfo.of(queryResult.getWriterId(), queryResult.getWriterNickName()));
    }
}
