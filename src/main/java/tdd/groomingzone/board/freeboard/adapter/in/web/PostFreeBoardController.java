package tdd.groomingzone.board.freeboard.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import tdd.groomingzone.board.freeboard.adapter.in.web.dto.FreeBoardApiDto;
import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.board.freeboard.application.port.in.usecase.PostFreeBoardUseCase;
import tdd.groomingzone.board.freeboard.application.port.in.command.PostFreeBoardCommand;

import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

@RestController
@RequestMapping("/free-board")
public class PostFreeBoardController {

    private final PostFreeBoardUseCase postFreeBoardUseCase;

    public PostFreeBoardController(PostFreeBoardUseCase postFreeBoardUseCase) {
        this.postFreeBoardUseCase = postFreeBoardUseCase;
    }

    @PostMapping
    public ResponseEntity<FreeBoardApiDto.Response> postFreeBoard(@AuthenticationPrincipal MemberEntity writer,
                                                                  @RequestBody FreeBoardApiDto.Post postDto) {
        PostFreeBoardCommand postFreeBoardCommand = PostFreeBoardCommand.of(writer.getId(), postDto.getTitle(), postDto.getContent());
        FreeBoardEntityCommandResponse commandResponse = postFreeBoardUseCase.postFreeBoard(postFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = FreeBoardApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
