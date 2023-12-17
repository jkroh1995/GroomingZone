package tdd.groomingzone.board.freeboard.adapter.in.web.put;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.board.freeboard.adapter.in.web.dto.FreeBoardApiDto;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.PutFreeBoardCommand;
import tdd.groomingzone.board.freeboard.application.port.in.PutFreeBoardUseCase;
import tdd.groomingzone.global.time.Time;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@RestController
@RequestMapping("/free-board")
public class PutFreeBoardController {

    private final PutFreeBoardUseCase putFreeBoardUseCase;
    private final Time time;

    public PutFreeBoardController(PutFreeBoardUseCase putFreeBoardUseCase, Time time) {
        this.putFreeBoardUseCase = putFreeBoardUseCase;
        this.time = time;
    }

    @PutMapping("/{free-board-id}")
    public ResponseEntity<FreeBoardApiDto.Response> putFreeBoard(@AuthenticationPrincipal MemberEntity requestMemberEntity,
                                                                 @PathVariable("free-board-id") long freeBoardId,
                                                                 @RequestBody FreeBoardApiDto.Put putDto) {
        PutFreeBoardCommand putFreeBoardCommand = PutFreeBoardCommand.of(requestMemberEntity, freeBoardId, putDto.getTitle(), putDto.getContent(), time.now());
        FreeBoardCommandResponse commandResult = putFreeBoardUseCase.putFreeBoard(putFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = FreeBoardApiDto.Response.builder()
                .boardId(commandResult.getBoardId())
                .title(commandResult.getTitle())
                .content(commandResult.getContent())
                .createdAt(commandResult.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(commandResult.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .writerInfo(commandResult.getWriterInfo())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}