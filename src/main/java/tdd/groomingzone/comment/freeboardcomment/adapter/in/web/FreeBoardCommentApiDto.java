package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import lombok.*;
import tdd.groomingzone.post.common.WriterInfo;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class FreeBoardCommentApiDto {

    public record Post(String content) {
    }

    @Builder
    public record Response(
            String content,
            String createdAt,
            String modifiedAt,
            WriterInfo writerInfo

    ) {
        public static FreeBoardCommentApiDto.Response of(SingleFreeBoardCommentResponse commandResponse) {
            return FreeBoardCommentApiDto.Response.builder()
                    .content(commandResponse.content())
                    .createdAt(commandResponse.createdAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .modifiedAt(commandResponse.modifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .writerInfo(commandResponse.writerInfo())
                    .build();
        }
    }

    public record Put(String content) {
    }
}
