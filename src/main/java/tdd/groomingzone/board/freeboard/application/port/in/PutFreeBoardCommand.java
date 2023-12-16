package tdd.groomingzone.board.freeboard.application.port.in;

import lombok.Data;
import tdd.groomingzone.member.entity.MemberEntity;

import java.time.LocalDateTime;

@Data
public final class PutFreeBoardCommand {
    private final MemberEntity writer;
    private final long freeBoardId;
    private final String title;
    private final String content;
    private final LocalDateTime modifiedAt;

    private PutFreeBoardCommand(MemberEntity writer, long freeBoardId, String title, String content, LocalDateTime modifiedAt) {
        this.writer = writer;
        this.freeBoardId = freeBoardId;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
    }

    public static PutFreeBoardCommand of(MemberEntity writer, long freeBoardId, String title, String content, LocalDateTime modifiedAt) {
        return new PutFreeBoardCommand(writer, freeBoardId, title, content, modifiedAt);
    }
}
