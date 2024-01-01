package tdd.groomingzone.comment.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public final class CommentInfo {
    private final String content;
    private final LocalDateTime modifiedAt;

    @Builder
    public CommentInfo(String content, LocalDateTime modifiedAt) {
        this.content = content;
        this.modifiedAt = modifiedAt;
    }
}
