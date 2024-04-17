package tdd.groomingzone.comment.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CommentInfo {
    private String content;
    private LocalDateTime modifiedAt;

    public void modify(String content, LocalDateTime modifiedAt) {
        this.content = content;
        this.modifiedAt = modifiedAt;
    }
}