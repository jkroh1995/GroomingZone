package tdd.groomingzone.board.freeboard.application.port.out;

import lombok.Getter;

@Getter
public final class SaveFreeBoardQuery {
    private final long writerId;
    private final String title;
    private final String content;

    private SaveFreeBoardQuery(long writerId, String title, String content) {
        this.writerId = writerId;
        this.title = title;
        this.content = content;
    }

    public static SaveFreeBoardQuery of(long writerId, String title, String content){
        return new SaveFreeBoardQuery(writerId, title, content);
    }
}
