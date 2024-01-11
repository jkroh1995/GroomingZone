package tdd.groomingzone.post.recruitment.application.port.in.command;

import lombok.Getter;

@Getter
public final class PostRecruitmentCommand {
    private final long writerId;
    private final String title;
    private final String content;
    private final String type;

    private PostRecruitmentCommand(long writerId, String title, String content, String type) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public static PostRecruitmentCommand of(long writerId, String title, String content, String type) {
        return new PostRecruitmentCommand(writerId, title, content, type);
    }
}
