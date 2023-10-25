package tdd.groomingzone.board;

import com.sun.istack.NotNull;
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

    public static class Response{

    }
}
