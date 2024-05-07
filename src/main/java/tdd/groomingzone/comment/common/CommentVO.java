package tdd.groomingzone.comment.common;

import java.time.LocalDateTime;

public record CommentVO(
        Long commentId,
        LocalDateTime createdAt
) {

}
