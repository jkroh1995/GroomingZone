package tdd.groomingzone.board.freeboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{free-board-id}")
    public ResponseEntity<FreeBoardDto.Response> putFreeBoard(@PathVariable("free-board-id") long freeBoardId,
                                                              @RequestBody FreeBoardDto.Put dto){
        FreeBoardDto.Response responseDto = freeBoardService.putFreeBoard(freeBoardId, dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
