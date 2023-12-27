package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.MultiFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.GetFreeBoardCommentPageCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.GetFreeBoardCommentUseCase;
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
        List<FreeBoardCommentApiDto.Response> responseDtoList = commandResponse.getPageResponse().stream()
                .map(FreeBoardCommentApiDto.Response::of)
                .collect(Collectors.toList());
        PagedResponseDto<FreeBoardCommentApiDto.Response> response = new PagedResponseDto<>(responseDtoList, commandResponse.getPageInfo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
