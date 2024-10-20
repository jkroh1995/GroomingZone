package tdd.groomingzone.post.freeboard.controller;

import com.fasterxml.jackson.databind.annotation.NoClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdd.groomingzone.post.freeboard.dto.DeleteFreeBoardCommand;
import tdd.groomingzone.post.freeboard.service.DeleteFreeBoardUseCase;

@RestController
@RequestMapping("/free-board")
public class DeleteFreeBoardController {

    private final DeleteFreeBoardUseCase deleteFreeBoardUseCase;

    public DeleteFreeBoardController(DeleteFreeBoardUseCase deleteFreeBoardUseCase) {
        this.deleteFreeBoardUseCase = deleteFreeBoardUseCase;
    }

    @DeleteMapping("/{free-board-id}")
    public ResponseEntity<NoClass> deleteFreeBoard(@AuthenticationPrincipal UserDetails requestMember,
                                                   @PathVariable("free-board-id") long freeBoardId) {
        DeleteFreeBoardCommand command = DeleteFreeBoardCommand.of(requestMember.getUsername(), freeBoardId);
        deleteFreeBoardUseCase.deleteFreeBoard(command);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
