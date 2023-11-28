package tdd.groomingzone.domain.board.freeboard.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.domain.board.freeboard.FreeBoard;
import tdd.groomingzone.domain.board.freeboard.FreeBoardDto;

@Service
public class FreeBoardConverter {

    public FreeBoard convertPostDtoToEntity(FreeBoardDto.Post postDto) {
        return FreeBoard.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
    }

    public FreeBoardDto.Response convertEntityToResponseDto(FreeBoard entity) {
        return FreeBoardDto.Response.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }
}
