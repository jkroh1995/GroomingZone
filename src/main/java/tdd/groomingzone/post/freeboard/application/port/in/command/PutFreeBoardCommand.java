package tdd.groomingzone.post.freeboard.application.port.in.command;

import java.time.LocalDateTime;

public record PutFreeBoardCommand(
        String requestMemberEmail,
        Long freeBoardId,
        String title,
        String content,
        LocalDateTime modifiedAt
)  {
    public static PutFreeBoardCommand of(String requestMemberEmail, Long freeBoardId, String title, String content, LocalDateTime modifiedAt) {
        return new PutFreeBoardCommand(requestMemberEmail, freeBoardId, title, content, modifiedAt);
    }
}
