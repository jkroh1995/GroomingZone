package tdd.groomingzone.comment.common.domain;

import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Getter
public final class CommentVO {
    private final long id;
    private final Member writer;
    private final LocalDateTime createdAt = LocalDateTime.now();

    private CommentVO(long id, Member writer) {
        this.id = id;
        this.writer = writer;
    }

    public static CommentVO of(long id, Member writer){
        return new CommentVO(id, writer);
    }
}
