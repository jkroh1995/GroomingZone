package tdd.groomingzone.board.freeboard.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import tdd.groomingzone.board.freeboard.application.port.in.FreeBoardDtoConverter;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardResult;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardUseCase;
import tdd.groomingzone.board.freeboard.application.port.in.PostFreeBoardCommand;

import tdd.groomingzone.member.entity.Member;
import tdd.groomingzone.global.time.Time;

@RestController
@RequestMapping("/free-board")
public class PostFreeBoardController {

    private final PostFreeBoardUseCase postFreeBoardUseCase;
    private final FreeBoardDtoConverter freeBoardDtoConverter;
    private final Time time;

    public PostFreeBoardController(PostFreeBoardUseCase postFreeBoardUseCase, FreeBoardDtoConverter freeBoardDtoConverter, Time time) {
        this.postFreeBoardUseCase = postFreeBoardUseCase;
        this.freeBoardDtoConverter = freeBoardDtoConverter;
        this.time = time;
    }

    @PostMapping
    public ResponseEntity<FreeBoardApiDto.Response> postFreeBoard(@AuthenticationPrincipal Member writer,
                                                                  @RequestBody FreeBoardApiDto.Post postDto) {
        PostFreeBoardCommand postFreeBoardCommand = freeBoardDtoConverter.convertApiPostDtoToCommand(writer, postDto);
        PostFreeBoardResult commandResult = postFreeBoardUseCase.postFreeBoard(postFreeBoardCommand);
        FreeBoardApiDto.Response responseDto = freeBoardDtoConverter.convertCommandResultToApiResponseDto(commandResult);
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
