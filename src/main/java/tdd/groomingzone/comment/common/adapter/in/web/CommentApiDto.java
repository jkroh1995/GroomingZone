package tdd.groomingzone.comment.common.adapter.in.web;

import lombok.*;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.comment.common.application.port.in.command.SingleCommentCommandResponse;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

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
        private final String content;
        private final String createdAt;
        private final String modifiedAt;
        private final WriterInfo writerInfo;

        public static CommentApiDto.Response of(SingleCommentCommandResponse commandResponse) {
            return CommentApiDto.Response.builder()
                    .content(commandResponse.getContent())
                    .createdAt(commandResponse.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .modifiedAt(commandResponse.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .writerInfo(commandResponse.getWriterInfo())
                    .build();
        }
    }
}
