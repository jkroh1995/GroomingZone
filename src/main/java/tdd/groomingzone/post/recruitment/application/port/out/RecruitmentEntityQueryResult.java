package tdd.groomingzone.post.recruitment.application.port.out;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class RecruitmentEntityQueryResult {
    private final Long boardId;
    private final Long writerId;
    private final String writerNickName;
    private final String title;
    private final String content;
    private final String type;
    private final int viewCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private RecruitmentEntityQueryResult(Long boardId, Long writerId, String writerNickName, String title, String content, String type, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.boardId = boardId;
        this.writerId = writerId;
        this.writerNickName = writerNickName;
        this.title = title;
        this.content = content;
        this.type = type;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static RecruitmentEntityQueryResult of(Long boardId, Long writerId, String writerNickName, String title, String content, String type, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new RecruitmentEntityQueryResult(boardId, writerId, writerNickName, title, content, type, viewCount, createdAt, modifiedAt);
    }
}
