package tdd.groomingzone.board.freeboard.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardResponse;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardUseCase;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardCommand;

import tdd.groomingzone.member.entity.Member;
import tdd.groomingzone.global.time.Time;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@RestController
@RequestMapping("/free-board")
public class PostFreeBoardController {

    private final PostFreeBoardUseCase postFreeBoardUseCase;
    private final Time time;

    public PostFreeBoardController(PostFreeBoardUseCase postFreeBoardUseCase,Time time) {
        this.postFreeBoardUseCase = postFreeBoardUseCase;
        this.time = time;
    }

    @PostMapping
    public ResponseEntity<FreeBoardApiDto.Response> postFreeBoard(@AuthenticationPrincipal Member writer,
                                                                  @RequestBody FreeBoardApiDto.Post postDto) {
        PostFreeBoardCommand postFreeBoardCommand = PostFreeBoardCommand.of(writer, postDto.getTitle(), postDto.getContent());
        PostFreeBoardResponse commandResult = postFreeBoardUseCase.postFreeBoard(postFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = FreeBoardApiDto.Response.builder()
                .boardId(commandResult.getBoardId())
                .title(commandResult.getTitle())
                .content(commandResult.getContent())
                .createdAt(commandResult.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .modifiedAt(commandResult.getModifiedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .writerInfo(commandResult.getWriterInfo())
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

//    @PutMapping("/{free-board-id}")
//    public ResponseEntity<FreeBoardDto.Response> putFreeBoard(@AuthenticationPrincipal Member requestMember,
//                                                              @PathVariable("free-board-id") long freeBoardId,
//                                                              @RequestBody FreeBoardDto.Put dto) {
//        FreeBoardDto.Response responseDto = freeBoardServiceManager.putFreeBoard(requestMember, freeBoardId, dto, time.now());
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{free-board-id}")
//    public ResponseEntity<NoClass> deleteFreeBoard(@AuthenticationPrincipal Member requestMember,
//                                                   @PathVariable("free-board-id") long freeBoardId) {
//        freeBoardServiceManager.deleteFreeBoard(requestMember, freeBoardId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/{free-board-id}")
//    public ResponseEntity<FreeBoardDto.Response> getFreeBoard(@PathVariable("free-board-id") long freeBoardId) {
//        FreeBoardDto.Response responseDto = freeBoardServiceManager.getFreeBoard(freeBoardId);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<PagedResponseDto<FreeBoardDto.Response>> getFreeBoardPage(@RequestParam(name = "page", defaultValue = "1") int pageNumber) {
//        PagedResponseDto<FreeBoardDto.Response> responseDtoList = freeBoardServiceManager.getFreeBoardPage(pageNumber);
//        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<PagedResponseDto<FreeBoardDto.Response>> getFilteredFreeBoardPage(@RequestParam(name = "title", required = false) String title,
//                                                                                            @RequestParam(name = "content", required = false) String content,
//                                                                                            @RequestParam(name = "writer", required = false) String writer,
//                                                                                            @RequestParam(name = "page", defaultValue = "1") int pageNumber) {
//        PagedResponseDto<FreeBoardDto.Response> responseDtoList = freeBoardServiceManager.getFilteredFreeBoardList(title, content, writer, pageNumber);
//        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
//    }
}
