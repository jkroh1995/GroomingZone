package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import lombok.*;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class FreeBoardCommentApiDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private String content;
    }

    @Getter
    @Builder
    public final static class Response {
        private final String content;
        private final String createdAt;
        private final String modifiedAt;
        private final WriterInfo writerInfo;

        public static FreeBoardCommentApiDto.Response of(SingleFreeBoardCommentResponse commandResponse) {
            return FreeBoardCommentApiDto.Response.builder()
                    .content(commandResponse.getContent())
                    .createdAt(commandResponse.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .modifiedAt(commandResponse.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .writerInfo(commandResponse.getWriterInfo())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Put {
        private String content;
    }
}
