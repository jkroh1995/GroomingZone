package tdd.groomingzone.domain.board.freeboard;

import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;

import java.time.LocalDateTime;

public interface FreeBoardService {

    FreeBoardDto.Response postFreeBoard(FreeBoardDto.Post postDto);

    FreeBoardDto.Response putFreeBoard(long id, FreeBoardDto.Put putDto, LocalDateTime modifiedAt);

    FreeBoardDto.Response getFreeBoard(long id);

    void deleteFreeBoard(long id);
}
