package tdd.groomingzone.domain.board.freeboard;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class FreeBoardDto {

    @Getter
    @Setter
    public static class Post {
        private String title;
        private String content;
    }

    @Getter
    @Builder
    public static class Response{
        private long id;
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class Put {
        private String title;
        private String content;
    }
}
