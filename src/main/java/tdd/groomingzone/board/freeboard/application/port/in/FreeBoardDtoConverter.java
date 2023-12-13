package tdd.groomingzone.board.freeboard.application.port.in;

import tdd.groomingzone.board.freeboard.adapter.in.web.FreeBoardApiDto;
import tdd.groomingzone.member.entity.Member;

public interface FreeBoardDtoConverter {
    PostFreeBoardCommand convertApiPostDtoToCommand(Member member, FreeBoardApiDto.Post postDto);

    FreeBoardApiDto.Response convertCommandResultToApiResponseDto(PostFreeBoardResult commandResult);
}
