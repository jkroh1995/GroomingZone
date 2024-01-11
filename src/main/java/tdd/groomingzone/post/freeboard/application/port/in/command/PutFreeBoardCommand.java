package tdd.groomingzone.post.freeboard.application.port.in.command;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class PutFreeBoardCommand {
    private final long writerId;
    private final long freeBoardId;
    private final String title;
    private final String content;
    private final LocalDateTime modifiedAt;

    private PutFreeBoardCommand(long writerId, long freeBoardId, String title, String content, LocalDateTime modifiedAt) {
        this.writerId = writerId;
        this.freeBoardId = freeBoardId;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
    }

    public static PutFreeBoardCommand of(long writerId, long freeBoardId, String title, String content, LocalDateTime modifiedAt) {
        return new PutFreeBoardCommand(writerId, freeBoardId, title, content, modifiedAt);
    }
}
