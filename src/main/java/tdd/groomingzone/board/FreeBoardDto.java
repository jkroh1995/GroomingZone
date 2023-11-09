package tdd.groomingzone.board;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class FreeBoardDto {

    @Getter
    @Setter
    public static class Post {
        @NotNull
        private String title;
        @NotNull
        private String content;
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private String title;
        private String content;
    }
}
