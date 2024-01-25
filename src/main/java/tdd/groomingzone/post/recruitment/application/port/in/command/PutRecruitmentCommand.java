package tdd.groomingzone.post.recruitment.application.port.in.command;

import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "of")
public final class PutRecruitmentCommand {
    private final String title;
    private final String content;
    private final long requestMemberId;
    private final long recruitmentId;
    private final LocalDateTime modifiedAt;
}
