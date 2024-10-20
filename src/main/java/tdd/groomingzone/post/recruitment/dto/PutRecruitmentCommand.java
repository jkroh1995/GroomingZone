package tdd.groomingzone.post.recruitment.dto;

import java.time.LocalDateTime;

public record PutRecruitmentCommand (
        String title,
        String content,
        String requestMemberEmail,
        Long recruitmentId,
        LocalDateTime modifiedAt
){
    public static PutRecruitmentCommand of(String title, String content, String requestMemberEmail, Long recruitmentId, LocalDateTime modifiedAt){
        return  new PutRecruitmentCommand(title, content, requestMemberEmail, recruitmentId, modifiedAt);
    }
}
