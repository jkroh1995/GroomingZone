package tdd.groomingzone.board.freeboard.application.port.in;

import lombok.Data;
import tdd.groomingzone.member.entity.MemberEntity;

@Data
public final class PostFreeBoardCommand {

    private final MemberEntity writer;
    private final String title;
    private final String content;

    private PostFreeBoardCommand(MemberEntity writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public static PostFreeBoardCommand of(MemberEntity writer, String title, String content) {
        return new PostFreeBoardCommand(writer, title, content);
    }
}
