package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.domain.board.freeboard.entity.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class FreeBoardConverter {

    public FreeBoard convertPostDtoToEntity(FreeBoardDto.Post postDto) {
        return FreeBoard.builder()
                .title(postDto.title)
                .content(postDto.content)
                .build();
    }

    public FreeBoardDto.Response convertEntityToResponseDto(FreeBoard entity) {
        return FreeBoardDto.Response.builder()
                .boardId(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(entity.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .build();
    }
}
