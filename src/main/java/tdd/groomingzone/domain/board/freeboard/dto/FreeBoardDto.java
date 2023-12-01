package tdd.groomingzone.domain.board.freeboard.dto;

import lombok.Builder;

public class FreeBoardDto {

    public static class Post {
        public String title;
        public String content;
    }

    @Builder
    public static class Response{
        public long boardId;
        public String title;
        public String content;
        public int viewCount;
        public String createdAt;
        public String modifiedAt;
    }

    public static class Put {
        public String title;
        public String content;
    }
}
