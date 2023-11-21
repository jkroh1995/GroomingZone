package tdd.groomingzone.board.freeboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/free-board")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;

    public FreeBoardController(FreeBoardService freeBoardService) {
        this.freeBoardService = freeBoardService;
    }

    @PostMapping
    public ResponseEntity<FreeBoardDto.Response> postFreeBoard(@RequestBody FreeBoardDto.Post dto) {
        FreeBoardDto.Response responseDto = freeBoardService.postFreeBoard(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
