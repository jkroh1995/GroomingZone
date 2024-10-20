package tdd.groomingzone.post.freeboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import tdd.groomingzone.post.freeboard.dto.FreeBoardApiDto;
import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.service.PostFreeBoardUseCase;
import tdd.groomingzone.post.freeboard.dto.PostFreeBoardCommand;

@RestController
@RequestMapping("/free-board")
public class PostFreeBoardController {

    private final PostFreeBoardUseCase postFreeBoardUseCase;

    public PostFreeBoardController(PostFreeBoardUseCase postFreeBoardUseCase) {
        this.postFreeBoardUseCase = postFreeBoardUseCase;
    }

    @PostMapping
    public ResponseEntity<FreeBoardApiDto.Response> postFreeBoard(@AuthenticationPrincipal UserDetails writer,
                                                                  @RequestBody FreeBoardApiDto.Post postDto) {
        PostFreeBoardCommand postFreeBoardCommand = PostFreeBoardCommand.of(writer.getUsername(), postDto.title(), postDto.content());
        SingleFreeBoardCommandResponse commandResponse = postFreeBoardUseCase.postFreeBoard(postFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = FreeBoardApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
