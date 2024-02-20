package tdd.groomingzone.post.freeboard.application.port.in.command;

import lombok.Data;

@Data
public final class PostFreeBoardCommand {

    private final String writerEmail;
    private final String title;
    private final String content;

    private PostFreeBoardCommand(String writerEmail, String title, String content) {
        this.writerEmail = writerEmail;
        this.title = title;
        this.content = content;
    }

    public static PostFreeBoardCommand of(String writerEmail, String title, String content) {
        return new PostFreeBoardCommand(writerEmail, title, content);
    }
}
