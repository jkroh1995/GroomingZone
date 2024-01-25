package tdd.groomingzone.post.freeboard.adapter.in.web.dto;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class FreeBoardApiDto {

    @Getter
    @Builder
    public final static class Post {
        private final String title;
        private final String content;
    }

    @Getter
    @Builder
    public final static class Response {
        private final long boardId;
        private final String title;
        private final String content;
        private final int viewCount;
        private final String createdAt;
        private final String modifiedAt;
        private final WriterInfo writerInfo;

        public static Response of(SingleFreeBoardCommandResponse commandResponse) {
            return Response.builder()
                    .boardId(commandResponse.getBoardId())
                    .title(commandResponse.getTitle())
                    .content(commandResponse.getContent())
                    .createdAt(commandResponse.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .modifiedAt(commandResponse.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .writerInfo(commandResponse.getWriterInfo())
                    .build();
        }
    }

    @Getter
    @Builder
    public final static class Put {
        private final String title;
        private final String content;
    }

    @Getter
    @Builder
    public final static class SimpleResponse {
        private final long boardId;
        private final String title;
        private final int viewCount;
        private final WriterInfo writerInfo;

        public static SimpleResponse of(SingleFreeBoardCommandResponse commandResponse) {
            return SimpleResponse.builder()
                    .boardId(commandResponse.getBoardId())
                    .title(commandResponse.getTitle())
                    .viewCount(commandResponse.getViewCount())
                    .writerInfo(commandResponse.getWriterInfo())
                    .build();
        }
    }
}
