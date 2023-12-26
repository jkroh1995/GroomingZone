package tdd.groomingzone.comment.common.domain;

import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Getter
public final class CommentVO {
    private final Member writer;
    private final LocalDateTime createdAt = LocalDateTime.now();

    private CommentVO(Member writer) {
        this.writer = writer;
    }

    public static CommentVO of(Member writer){
        return new CommentVO(writer);
    }
}
