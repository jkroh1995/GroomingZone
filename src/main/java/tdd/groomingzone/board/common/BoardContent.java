package tdd.groomingzone.board.common;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.member.entity.Member;

import java.time.LocalDateTime;

@Getter
public class BoardContent {
    private final long id;

    private final Member member;

    private String title;

    private String content;

    private int viewCount;

    private final LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    public BoardContent(long id, Member member, String title, String content, int viewCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
