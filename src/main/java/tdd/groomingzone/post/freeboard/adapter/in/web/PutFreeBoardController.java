package tdd.groomingzone.post.freeboard.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.post.freeboard.adapter.in.web.dto.FreeBoardApiDto;
import tdd.groomingzone.post.freeboard.application.port.in.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.PutFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.PutFreeBoardUseCase;
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
        PutFreeBoardCommand putFreeBoardCommand = PutFreeBoardCommand.of(requestMemberEntity.getId(), freeBoardId, putDto.getTitle(), putDto.getContent(), time.now());
        SingleFreeBoardCommandResponse commandResponse = putFreeBoardUseCase.putFreeBoard(putFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = FreeBoardApiDto.Response.builder()
                .boardId(commandResponse.getBoardId())
                .title(commandResponse.getTitle())
                .content(commandResponse.getContent())
                .createdAt(commandResponse.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(commandResponse.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .writerInfo(commandResponse.getWriterInfo())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
