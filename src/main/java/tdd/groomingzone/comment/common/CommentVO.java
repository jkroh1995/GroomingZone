package tdd.groomingzone.comment.common;

import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Getter
public final class CommentVO {
    private final long id;
    private final Member writer;
    private final LocalDateTime createdAt;

    private CommentVO(long id, Member writer, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    public static CommentVO of(long id, Member writer, LocalDateTime createdAt){
        return new CommentVO(id, writer, createdAt);
    }
}
