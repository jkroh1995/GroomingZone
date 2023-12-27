package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.comment.common.application.port.in.command.MultiCommentCommandResponse;
import tdd.groomingzone.comment.common.adapter.in.web.CommentApiDto;
import tdd.groomingzone.comment.common.application.port.in.command.GetCommentCommand;
import tdd.groomingzone.comment.common.application.port.in.usecase.GetCommentUseCase;
import tdd.groomingzone.global.pagedresponse.PagedResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/free-board/comment")
public class GetFreeBoardCommentController {

    private final GetCommentUseCase getCommentUseCase;

    public GetFreeBoardCommentController(GetCommentUseCase getCommentUseCase) {
        this.getCommentUseCase = getCommentUseCase;
    }

    @GetMapping("/{free-board-id}")
    public ResponseEntity<PagedResponseDto<CommentApiDto.Response>> getFreeBoardCommentList(@PathVariable("free-board-id") long boardId,
                                                                                            @RequestParam(name = "page", defaultValue = "1") int pageNumber){
        GetCommentCommand command = GetCommentCommand.of(boardId, pageNumber);
        MultiCommentCommandResponse commandResponse = getCommentUseCase.getFreeBoardComment(command);
        List<CommentApiDto.Response> responseDtoList = commandResponse.getPageResponse().stream()
                .map(CommentApiDto.Response::of)
                .collect(Collectors.toList());
        PagedResponseDto<CommentApiDto.Response> response = new PagedResponseDto<>(responseDtoList, commandResponse.getPageInfo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
