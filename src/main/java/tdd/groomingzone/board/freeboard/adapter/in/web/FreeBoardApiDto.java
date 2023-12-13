package tdd.groomingzone.board.freeboard.adapter.in.web;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tdd.groomingzone.board.common.WriterInfo;

public class FreeBoardApiDto {

    @Getter
    @Setter
    public static class Post {
        private String title;
        private String content;
    }

    @Getter
    @Setter
    @Builder
    public static class Response{
        private long boardId;
        private String title;
        private String content;
        private int viewCount;
        private String createdAt;
        private String modifiedAt;
        private WriterInfo writerInfo;
    }

    @Getter
    @Setter
    public static class Put {
        private String title;
        private String content;
    }
}
