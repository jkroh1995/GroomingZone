package tdd.groomingzone.post.freeboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.post.freeboard.dto.FreeBoardApiDto;
import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.PutFreeBoardCommand;
import tdd.groomingzone.post.freeboard.service.PutFreeBoardUseCase;
import tdd.groomingzone.global.time.Time;

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
    public ResponseEntity<FreeBoardApiDto.Response> putFreeBoard(@AuthenticationPrincipal UserDetails requestMember,
                                                                 @PathVariable("free-board-id") long freeBoardId,
                                                                 @RequestBody FreeBoardApiDto.Put putDto) {
        PutFreeBoardCommand putFreeBoardCommand = PutFreeBoardCommand.of(requestMember.getUsername(), freeBoardId, putDto.title(), putDto.content(), time.now());
        SingleFreeBoardCommandResponse commandResponse = putFreeBoardUseCase.putFreeBoard(putFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = FreeBoardApiDto.Response.builder()
                .boardId(commandResponse.boardId())
                .title(commandResponse.title())
                .content(commandResponse.content())
                .createdAt(commandResponse.createdAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(commandResponse.modifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .writerInfo(commandResponse.writerInfo())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
