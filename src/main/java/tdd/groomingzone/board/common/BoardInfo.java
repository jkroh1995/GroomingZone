package tdd.groomingzone.board.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardInfo {

    private Title title;

    private Content content;

    private int viewCount;

    private LocalDateTime modifiedAt;

    @Builder
    public BoardInfo(String title, String content, int viewCount, LocalDateTime modifiedAt) {
        this.title = Title.of(title);
        this.content = Content.of(content);
        this.viewCount = viewCount;
        this.modifiedAt = modifiedAt;
    }

    public void setTitle(String title) {
        this.title = Title.of(title);
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
