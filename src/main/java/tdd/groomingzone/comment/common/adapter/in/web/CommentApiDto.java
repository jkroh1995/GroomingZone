package tdd.groomingzone.comment.common.adapter.in.web;

import lombok.*;

public final class CommentApiDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String content;
    }

    @Getter
    @Builder
    public final static class Response {
        private final long boardId;
        private final long commentId;
        private final String content;
        private final String createdAt;
        private final String modifiedAt;
    }
}
