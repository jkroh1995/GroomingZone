package tdd.groomingzone.post.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardInfo {

    private final Title title;

    private final Content content;

    private int viewCount;

    private final LocalDateTime modifiedAt;

    @Builder
    public BoardInfo(String title, String content, int viewCount, LocalDateTime modifiedAt) {
        this.title = Title.of(title);
        this.content = Content.of(content);
        this.viewCount = viewCount;
        this.modifiedAt = modifiedAt;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
