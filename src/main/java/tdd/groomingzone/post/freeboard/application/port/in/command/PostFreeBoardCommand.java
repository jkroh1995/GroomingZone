package tdd.groomingzone.post.freeboard.application.port.in.command;

public record PostFreeBoardCommand(
        String writerEmail,
        String title,
        String content

) {
    public static PostFreeBoardCommand of(String writerEmail, String title, String content) {
        return new PostFreeBoardCommand(writerEmail, title, content);
    }
}
