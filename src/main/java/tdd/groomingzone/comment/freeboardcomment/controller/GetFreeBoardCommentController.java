package tdd.groomingzone.comment.freeboardcomment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.comment.freeboardcomment.dto.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.dto.GetFreeBoardCommentPageCommand;
import tdd.groomingzone.comment.freeboardcomment.service.GetFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.dto.FreeBoardCommentApiDto;
import tdd.groomingzone.global.pagedresponse.PagedResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/free-board/comment")
public class GetFreeBoardCommentController {

    private final GetFreeBoardCommentUseCase getFreeBoardCommentUseCase;

    public GetFreeBoardCommentController(GetFreeBoardCommentUseCase getFreeBoardCommentUseCase) {
        this.getFreeBoardCommentUseCase = getFreeBoardCommentUseCase;
    }

    @GetMapping("/{free-board-id}")
    public ResponseEntity<PagedResponseDto<FreeBoardCommentApiDto.Response>> getFreeBoardCommentList(@PathVariable("free-board-id") long boardId,
                                                                                                     @RequestParam(name = "page", defaultValue = "1") int pageNumber){
        GetFreeBoardCommentPageCommand command = GetFreeBoardCommentPageCommand.of(boardId, pageNumber);
        MultiFreeBoardCommentResponse commandResponse = getFreeBoardCommentUseCase.getFreeBoardComment(command);
        List<FreeBoardCommentApiDto.Response> responseDtoList = commandResponse.pageResponse().stream()
                .map(FreeBoardCommentApiDto.Response::of)
                .collect(Collectors.toList());
        PagedResponseDto<FreeBoardCommentApiDto.Response> response = new PagedResponseDto<>(responseDtoList, commandResponse.pageInfo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
