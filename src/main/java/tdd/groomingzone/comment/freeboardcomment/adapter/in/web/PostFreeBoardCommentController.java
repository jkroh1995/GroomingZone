package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PostFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.PostFreeBoardCommentUseCase;

@RestController
@RequestMapping("/free-board/comment")
public class PostFreeBoardCommentController {

    private final PostFreeBoardCommentUseCase postFreeBoardCommentUseCase;

    public PostFreeBoardCommentController(PostFreeBoardCommentUseCase postFreeBoardCommentUseCase) {
        this.postFreeBoardCommentUseCase = postFreeBoardCommentUseCase;
    }

    @PostMapping("/{free-board-id}")
    public ResponseEntity<FreeBoardCommentApiDto.Response> postFreeBoardComment(@AuthenticationPrincipal UserDetails writer,
                                               @PathVariable("free-board-id") long boardId,
                                               @RequestBody FreeBoardCommentApiDto.Post dto) {
        PostFreeBoardCommentCommand postFreeBoardCommentCommand = PostFreeBoardCommentCommand.of(writer.getUsername(), boardId, dto.getContent());
        SingleFreeBoardCommentResponse commentResponse = postFreeBoardCommentUseCase.postFreeBoardComment(postFreeBoardCommentCommand);
        FreeBoardCommentApiDto.Response responseDto = FreeBoardCommentApiDto.Response.of(commentResponse);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
