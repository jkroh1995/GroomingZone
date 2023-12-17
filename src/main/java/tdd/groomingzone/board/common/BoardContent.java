package tdd.groomingzone.board.common;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Getter
public class BoardContent {
    private final long id;

    private final Member writer;

    private Title title;

    private String content;

    private int viewCount;

    private final LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    public BoardContent(long id, Member writer, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.writer = writer;
        this.title = Title.of(title);
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setTitle(String title) {
        this.title = Title.of(title);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
