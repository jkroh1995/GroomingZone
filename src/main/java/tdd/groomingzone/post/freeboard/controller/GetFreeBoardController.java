package tdd.groomingzone.post.freeboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.post.freeboard.dto.FreeBoardApiDto;
import tdd.groomingzone.post.freeboard.dto.SingleFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.GetFreeBoardCommand;
import tdd.groomingzone.post.freeboard.service.GetFreeBoardUseCase;

@RestController
@RequestMapping("/free-board")
public class GetFreeBoardController {

    private final GetFreeBoardUseCase getFreeBoardUseCase;

    public GetFreeBoardController(GetFreeBoardUseCase getFreeBoardUseCase) {
        this.getFreeBoardUseCase = getFreeBoardUseCase;
    }

    @GetMapping("/{free-board-id}")
    public ResponseEntity<FreeBoardApiDto.Response> getFreeBoard(@PathVariable("free-board-id") Long freeBoardId) {
        GetFreeBoardCommand getFreeBoardCommand = GetFreeBoardCommand.of(freeBoardId);
        SingleFreeBoardCommandResponse commandResponse = getFreeBoardUseCase.getFreeBoard(getFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = FreeBoardApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
