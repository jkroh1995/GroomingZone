package tdd.groomingzone.domain.board.freeboard;

import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.member.entity.Member;
import tdd.groomingzone.global.pagedresponse.PagedResponseDto;

import java.time.LocalDateTime;

public interface FreeBoardService {

    FreeBoardDto.Response postFreeBoard(Member writer, FreeBoardDto.Post postDto);

    FreeBoardDto.Response putFreeBoard(long id, FreeBoardDto.Put putDto, LocalDateTime modifiedAt);

    FreeBoardDto.Response getFreeBoard(long id);

    PagedResponseDto<FreeBoardDto.Response> getFreeBoardPage(int pageNumber);

    PagedResponseDto<FreeBoardDto.Response> getFilteredFreeBoardList(String title, String content, String writer, int pageNumber);

    void deleteFreeBoard(long id);
}
