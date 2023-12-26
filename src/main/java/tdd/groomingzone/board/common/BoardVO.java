package tdd.groomingzone.board.common;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Getter
public final class BoardVO {
    private final Long id;

    private final Member writer;

    private final LocalDateTime createdAt;

    @Builder
    public BoardVO(long id, Member writer, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.createdAt = createdAt;
    }
}