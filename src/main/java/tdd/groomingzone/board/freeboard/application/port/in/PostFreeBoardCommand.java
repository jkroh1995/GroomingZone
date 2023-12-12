package tdd.groomingzone.board.freeboard.application.port.in;

import lombok.Getter;
import tdd.groomingzone.member.entity.Member;

@Getter
public final class PostFreeBoardCommand {

    private final Member writer;
    private final String title;
    private final String content;

    private PostFreeBoardCommand(Member writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public static PostFreeBoardCommand of(Member writer, String title, String content) {
        return new PostFreeBoardCommand(writer, title, content);
    }
}
