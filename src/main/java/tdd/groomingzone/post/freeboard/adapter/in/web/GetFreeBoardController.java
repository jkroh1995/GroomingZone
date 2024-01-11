package tdd.groomingzone.post.freeboard.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.post.freeboard.adapter.in.web.dto.FreeBoardApiDto;
import tdd.groomingzone.post.freeboard.application.port.in.FreeBoardEntityCommandResponse;
import tdd.groomingzone.post.freeboard.application.port.in.command.GetFreeBoardCommand;
import tdd.groomingzone.post.freeboard.application.port.in.usecase.GetFreeBoardUseCase;

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
        FreeBoardEntityCommandResponse commandResponse = getFreeBoardUseCase.getFreeBoard(getFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = FreeBoardApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
