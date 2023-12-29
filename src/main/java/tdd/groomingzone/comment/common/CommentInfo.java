package tdd.groomingzone.comment.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentInfo {
    private String content;
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @Builder
    public CommentInfo(String content) {
        this.content = content;
    }
}
