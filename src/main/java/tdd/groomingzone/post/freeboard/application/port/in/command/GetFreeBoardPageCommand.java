package tdd.groomingzone.post.freeboard.application.port.in.command;

import lombok.Data;

@Data
public final class GetFreeBoardPageCommand {
    private final String title;
    private final String content;
    private final String writerNickName;
    private final int pageNumber;

    private GetFreeBoardPageCommand(String title, String content, String writerNickName, int pageNumber) {
        this.title = title;
        this.content = content;
        this.writerNickName = writerNickName;
        this.pageNumber = pageNumber;
    }

    public static GetFreeBoardPageCommand of(String title, String content, String writerNickName, int pageNumber) {
        return new GetFreeBoardPageCommand(title, content, writerNickName, pageNumber);
    }
}
