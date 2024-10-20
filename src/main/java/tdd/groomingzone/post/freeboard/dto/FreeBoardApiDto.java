package tdd.groomingzone.post.freeboard.dto;

import lombok.Builder;
import tdd.groomingzone.post.common.WriterInfo;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class FreeBoardApiDto {

    @Builder
    public record Post(String title, String content) {
    }

    @Builder
    public record Response(long boardId, String title, String content, int viewCount, String createdAt,
                           String modifiedAt, WriterInfo writerInfo) {
        public static Response of(SingleFreeBoardCommandResponse commandResponse) {
            return Response.builder()
                    .boardId(commandResponse.boardId())
                    .title(commandResponse.title())
                    .content(commandResponse.content())
                    .createdAt(commandResponse.createdAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .modifiedAt(commandResponse.modifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .writerInfo(commandResponse.writerInfo())
                    .build();
        }
    }

        @Builder
        public record Put(String title, String content) {
    }

    @Builder
    public record SimpleResponse (
            long boardId,
            String title,
            int viewCount,
            WriterInfo writerInfo
    ){
        public static SimpleResponse of(SingleFreeBoardCommandResponse commandResponse) {
            return SimpleResponse.builder()
                    .boardId(commandResponse.boardId())
                    .title(commandResponse.title())
                    .viewCount(commandResponse.viewCount())
                    .writerInfo(commandResponse.writerInfo())
                    .build();
        }
    }
}
