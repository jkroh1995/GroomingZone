package tdd.groomingzone.comment.freeboardcomment.dto;

import lombok.*;
import tdd.groomingzone.post.common.WriterInfo;

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
