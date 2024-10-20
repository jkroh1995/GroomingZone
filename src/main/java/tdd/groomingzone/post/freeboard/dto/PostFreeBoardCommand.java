package tdd.groomingzone.post.freeboard.dto;

public record PostFreeBoardCommand(
        String writerEmail,
        String title,
        String content

) {
    public static PostFreeBoardCommand of(String writerEmail, String title, String content) {
        return new PostFreeBoardCommand(writerEmail, title, content);
    }
}
