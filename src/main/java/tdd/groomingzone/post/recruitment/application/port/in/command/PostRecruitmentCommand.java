package tdd.groomingzone.post.recruitment.application.port.in.command;

import lombok.Getter;

@Getter
public final class PostRecruitmentCommand {
    private final String writerEmail;
    private final String title;
    private final String content;
    private final String type;

    private PostRecruitmentCommand(String writerEmail, String title, String content, String type) {
        this.writerEmail = writerEmail;
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public static PostRecruitmentCommand of(String writerEmail, String title, String content, String type) {
        return new PostRecruitmentCommand(writerEmail, title, content, type);
    }
}
