package tdd.groomingzone.post.recruitment.application.port.out;

import lombok.Getter;
import tdd.groomingzone.post.recruitment.domain.Recruitment;

import java.time.LocalDateTime;

@Getter
public final class SaveRecruitmentQuery {

    private final Long writerId;
    private final String writerNickName;
    private final Long boardId;
    private final String title;
    private final String content;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String type;

    private SaveRecruitmentQuery(Long writerId, String writerNickName, Long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String type) {
        this.writerId = writerId;
        this.writerNickName = writerNickName;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.type = type;
    }

    public static SaveRecruitmentQuery of(Recruitment recruitment) {
        return new SaveRecruitmentQuery(recruitment.getWriterId(),
                recruitment.getWriterNickName(),
                recruitment.getId(),
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.getViewCount(),
                recruitment.getCreatedAt(),
                recruitment.getModifiedAt(),
                recruitment.getType());
    }
}
