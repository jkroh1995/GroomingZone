package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PutFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.PutFreeBoardCommentUseCase;

@RestController
@RequestMapping("/free-board/comment")
public class PutFreeBoardCommentController {

    private final PutFreeBoardCommentUseCase putFreeBoardCommentUseCase;

    public PutFreeBoardCommentController(PutFreeBoardCommentUseCase putFreeBoardCommentUseCase) {
        this.putFreeBoardCommentUseCase = putFreeBoardCommentUseCase;
    }

    @PutMapping("/{free-board-id}/{comment-id}")
    public ResponseEntity<FreeBoardCommentApiDto.Response> putFreeBoardComment(@AuthenticationPrincipal UserDetails requestMember,
                                                                               @PathVariable("free-board-id") long freeBoardId,
                                                                               @PathVariable("comment-id") long commentId,
                                                                               @RequestBody FreeBoardCommentApiDto.Put dto) {
        PutFreeBoardCommentCommand command = PutFreeBoardCommentCommand.of(requestMember.getUsername(), freeBoardId, commentId, dto.content());
        SingleFreeBoardCommentResponse commandResponse = putFreeBoardCommentUseCase.putFreeBoard(command);
        FreeBoardCommentApiDto.Response responseDto = FreeBoardCommentApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
