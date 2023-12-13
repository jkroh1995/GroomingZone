package tdd.groomingzone.board.freeboard.application.service;

import org.springframework.stereotype.Component;

import tdd.groomingzone.board.freeboard.adapter.in.web.FreeBoardApiDto;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardDtoConverter;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardResult;
import tdd.groomingzone.member.entity.Member;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Component
public class FreeBoardDtoConverterImpl implements FreeBoardDtoConverter {

    public PostFreeBoardCommand convertApiPostDtoToCommand(Member member, FreeBoardApiDto.Post postDto){
        return PostFreeBoardCommand.of(member, postDto.getTitle(), postDto.getContent());
    }

    public FreeBoardApiDto.Response convertCommandResultToApiResponseDto(PostFreeBoardResult commandResult) {
        return FreeBoardApiDto.Response.builder()
                .boardId(commandResult.getBoardId())
                .title(commandResult.getTitle())
                .content(commandResult.getContent())
                .createdAt(commandResult.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(commandResult.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .writerInfo(commandResult.getWriterInfo())
                .build();
    }
}
