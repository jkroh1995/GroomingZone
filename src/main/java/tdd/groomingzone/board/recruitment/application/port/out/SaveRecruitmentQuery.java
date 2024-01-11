package tdd.groomingzone.board.recruitment.application.port.out;

import lombok.Getter;

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

    public static SaveRecruitmentQuery of(Long writerId, String writerNickName, Long boardId, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt, String type) {
        return new SaveRecruitmentQuery(writerId, writerNickName, boardId, title, content, viewCount, createdAt, modifiedAt, type);
    }
}
