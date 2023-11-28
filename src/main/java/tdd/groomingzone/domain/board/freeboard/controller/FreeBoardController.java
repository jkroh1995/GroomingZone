package tdd.groomingzone.domain.board.freeboard.controller;

import com.fasterxml.jackson.databind.annotation.NoClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.domain.board.freeboard.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.FreeBoardService;

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

    @GetMapping("/{free-board-id}")
    public ResponseEntity<FreeBoardDto.Response> getFreeBoard(@PathVariable("free-board-id") long freeBoardId){
        FreeBoardDto.Response responseDto = freeBoardService.getFreeBoard(freeBoardId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{free-board-id}")
    public ResponseEntity<NoClass> deleteFreeBoard(@PathVariable("free-board-id") long freeBoardId){
        freeBoardService.deleteFreeBoard(freeBoardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
