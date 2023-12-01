package tdd.groomingzone.domain.board.freeboard.controller;

import com.fasterxml.jackson.databind.annotation.NoClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.domain.board.freeboard.dto.FreeBoardDto;
import tdd.groomingzone.domain.board.freeboard.FreeBoardService;
import tdd.groomingzone.global.time.Time;

import java.util.List;

@RestController
@RequestMapping("/free-board")
public class FreeBoardController {

    private final FreeBoardService freeBoardService;
    private final Time time;

    public FreeBoardController(FreeBoardService freeBoardService, Time time) {
        this.freeBoardService = freeBoardService;
        this.time = time;
    }

    @PostMapping
    public ResponseEntity<FreeBoardDto.Response> postFreeBoard(@RequestBody FreeBoardDto.Post dto) {
        FreeBoardDto.Response responseDto = freeBoardService.postFreeBoard(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{free-board-id}")
    public ResponseEntity<FreeBoardDto.Response> putFreeBoard(@PathVariable("free-board-id") long freeBoardId,
                                                              @RequestBody FreeBoardDto.Put dto) {
        FreeBoardDto.Response responseDto = freeBoardService.putFreeBoard(freeBoardId, dto, time.now());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{free-board-id}")
    public ResponseEntity<FreeBoardDto.Response> getFreeBoard(@PathVariable("free-board-id") long freeBoardId) {
        FreeBoardDto.Response responseDto = freeBoardService.getFreeBoard(freeBoardId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FreeBoardDto.Response>> getFreeBoardPage(@RequestParam(name = "page", defaultValue = "1") int pageNumber) {
        List<FreeBoardDto.Response> responseDtoList = freeBoardService.getFreeBoardPage(pageNumber);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FreeBoardDto.Response>> getFilteredFreeBoardPage(@RequestParam(name = "title", required = false) String title,
                                                                                @RequestParam(name = "content", required = false) String content,
                                                                                @RequestParam(name = "writer", required = false) String writer,
                                                                                @RequestParam(name = "page", defaultValue = "1") int pageNumber) {
        List<FreeBoardDto.Response> responseDtoList = freeBoardService.getFilteredFreeBoardList(title, content, writer, pageNumber);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{free-board-id}")
    public ResponseEntity<NoClass> deleteFreeBoard(@PathVariable("free-board-id") long freeBoardId) {
        freeBoardService.deleteFreeBoard(freeBoardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
