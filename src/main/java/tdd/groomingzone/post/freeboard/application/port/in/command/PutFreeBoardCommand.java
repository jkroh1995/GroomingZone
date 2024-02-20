package tdd.groomingzone.post.freeboard.application.port.in.command;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class PutFreeBoardCommand {
    private final String requestMemberEmail;
    private final long freeBoardId;
    private final String title;
    private final String content;
    private final LocalDateTime modifiedAt;

    private PutFreeBoardCommand(String requestMemberEmail, long freeBoardId, String title, String content, LocalDateTime modifiedAt) {
        this.requestMemberEmail = requestMemberEmail;
        this.freeBoardId = freeBoardId;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
    }

    public static PutFreeBoardCommand of(String requestMemberEmail, long freeBoardId, String title, String content, LocalDateTime modifiedAt) {
        return new PutFreeBoardCommand(requestMemberEmail, freeBoardId, title, content, modifiedAt);
    }
}
