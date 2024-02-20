package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import com.fasterxml.jackson.databind.annotation.NoClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.DeleteFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.DeleteFreeBoardCommentUseCase;

@RestController
@RequestMapping("/free-board/comment")
public class DeleteFreeBoardCommentController {

    private final DeleteFreeBoardCommentUseCase deleteFreeBoardCommentUseCase;

    public DeleteFreeBoardCommentController(DeleteFreeBoardCommentUseCase deleteFreeBoardCommentUseCase) {
        this.deleteFreeBoardCommentUseCase = deleteFreeBoardCommentUseCase;
    }

    @DeleteMapping("/{free-board-id}/{comment-id}")
    public ResponseEntity<NoClass> deleteFreeBoardComment(@AuthenticationPrincipal UserDetails requestMember,
                                                          @PathVariable("free-board-id") long freeBoardId,
                                                          @PathVariable("comment-id") long commentId){
        DeleteFreeBoardCommentCommand command = DeleteFreeBoardCommentCommand.of(requestMember.getUsername(), freeBoardId, commentId);
        deleteFreeBoardCommentUseCase.delete(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
