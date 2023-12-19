package tdd.groomingzone.board.freeboard.application.port.in.command;

import lombok.Data;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

@Data
public final class PostFreeBoardCommand {

    private final Long writerId;
    private final String title;
    private final String content;

    private PostFreeBoardCommand(Long writerId, String title, String content) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
    }

    public static PostFreeBoardCommand of(Long writerId, String title, String content) {
        return new PostFreeBoardCommand(writerId, title, content);
    }
}
