package tdd.groomingzone.board.recruitment.adapter.in.web;

import lombok.Builder;
import lombok.Getter;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.recruitment.application.port.in.SingleRecruitmentResponse;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class RecruitmentApiDto {

    @Getter
    @Builder
    public static final class Post{
        private final String title;
        private final String content;
        private final String type;
    }

    @Getter
    @Builder
    public static final class Response {
        private final long boardId;
        private final String title;
        private final String content;
        private final String type;
        private final int viewCount;
        private final String createdAt;
        private final String modifiedAt;
        private final WriterInfo writerInfo;

        public static Response of(SingleRecruitmentResponse commandResponse) {
            return Response.builder()
                    .boardId(commandResponse.getBoardId())
                    .title(commandResponse.getTitle())
                    .content(commandResponse.getContent())
                    .type(commandResponse.getType())
                    .viewCount(commandResponse.getViewCount())
                    .createdAt(commandResponse.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .modifiedAt(commandResponse.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                    .writerInfo(commandResponse.getWriterInfo())
                    .build();
        }
    }
}
