package tdd.groomingzone.post.recruitment.dto;

public record PostRecruitmentCommand(
        String writerEmail,
        String title,
        String content,
        String type
)  {
    public static PostRecruitmentCommand of(String writerEmail, String title, String content, String type) {
        return new PostRecruitmentCommand(writerEmail, title, content, type);
    }
}
