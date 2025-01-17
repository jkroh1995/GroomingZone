package tdd.groomingzone.post.freeboard.dto;

public record GetFreeBoardPageCommand(
        String title,
        String content,
        String writerNickName,
        int pageNumber
) {
    public static GetFreeBoardPageCommand of(String title, String content, String writerNickName, int pageNumber) {
        return new GetFreeBoardPageCommand(title, content, writerNickName, pageNumber);
    }
}
