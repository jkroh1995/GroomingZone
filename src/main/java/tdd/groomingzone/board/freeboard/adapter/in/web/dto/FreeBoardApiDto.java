package tdd.groomingzone.board.freeboard.adapter.in.web.dto;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.board.comment.CommentApiDto;
import tdd.groomingzone.board.common.WriterInfo;

import java.util.List;

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
        private final long boardId;
        private final String title;
        private final String content;
        private final int viewCount;
        private final String createdAt;
        private final String modifiedAt;
        private final WriterInfo writerInfo;
        private final List<CommentApiDto> comments;
    }

    @Getter
    @Builder
    public final static class Put {
        private final String title;
        private final String content;
    }
}
