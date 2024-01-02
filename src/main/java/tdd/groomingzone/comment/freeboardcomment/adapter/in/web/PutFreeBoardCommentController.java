package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PutFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.PutFreeBoardCommentUseCase;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

@RestController
@RequestMapping("/free-board/comment")
public class PutFreeBoardCommentController {

    private final PutFreeBoardCommentUseCase putFreeBoardCommentUseCase;

    public PutFreeBoardCommentController(PutFreeBoardCommentUseCase putFreeBoardCommentUseCase) {
        this.putFreeBoardCommentUseCase = putFreeBoardCommentUseCase;
    }

    @PutMapping("/{free-board-id}/{comment-id}")
    public ResponseEntity<FreeBoardCommentApiDto.Response> putFreeBoardComment(@AuthenticationPrincipal MemberEntity requestMember,
                                                                               @PathVariable("free-board-id") long freeBoardId,
                                                                               @PathVariable("comment-id") long commentId,
                                                                               @RequestBody FreeBoardCommentApiDto.Put dto) {
        PutFreeBoardCommentCommand command = PutFreeBoardCommentCommand.of(requestMember.getId(), freeBoardId, commentId, dto.getContent());
        SingleFreeBoardCommentResponse commandResponse = putFreeBoardCommentUseCase.putFreeBoard(command);
        FreeBoardCommentApiDto.Response responseDto = FreeBoardCommentApiDto.Response.of(commandResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
