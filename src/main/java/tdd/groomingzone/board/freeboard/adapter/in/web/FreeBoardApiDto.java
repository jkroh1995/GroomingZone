package tdd.groomingzone.board.freeboard.adapter.in.web;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tdd.groomingzone.board.common.WriterInfo;

public final class FreeBoardApiDto {

    @Getter
    @Builder
    public final static class Post {
        private final String title;
        private final String content;
    }

    @Getter
    @Builder
    public final static class Response{
        private long boardId;
        private String title;
        private String content;
        private int viewCount;
        private String createdAt;
        private String modifiedAt;
        private WriterInfo writerInfo;
    }

    @Getter
    @Builder
    public final static class Put {
        private final String title;
        private final String content;
    }
}
