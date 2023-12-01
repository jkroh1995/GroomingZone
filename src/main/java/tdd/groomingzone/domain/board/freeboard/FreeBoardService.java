package tdd.groomingzone.domain.board.freeboard;

import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;

import java.time.LocalDateTime;
import java.util.List;

public interface FreeBoardService {

    FreeBoardDto.Response postFreeBoard(FreeBoardDto.Post postDto);

    FreeBoardDto.Response putFreeBoard(long id, FreeBoardDto.Put putDto, LocalDateTime modifiedAt);

    FreeBoardDto.Response getFreeBoard(long id);

    List<FreeBoardDto.Response> getFreeBoardPage(int pageNumber);

    List<FreeBoardDto.Response> getFilteredFreeBoardList(String title, String content, String writer, int pageNumber);

    void deleteFreeBoard(long id);
}
