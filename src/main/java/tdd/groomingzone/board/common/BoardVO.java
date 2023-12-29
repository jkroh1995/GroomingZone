package tdd.groomingzone.board.common;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public final class BoardVO {
    private final long id;

    private final Member writer;

    private final LocalDateTime createdAt;

    @Builder
    public BoardVO(long id, Member writer, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(createdAt.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BoardVO){
            return this.id == ((BoardVO) obj).id
                    && this.writer.getEmail().equals(((BoardVO) obj).getWriter().getEmail())
                    && this.createdAt == ((BoardVO) obj).createdAt;
        }
        return false;
    }
}