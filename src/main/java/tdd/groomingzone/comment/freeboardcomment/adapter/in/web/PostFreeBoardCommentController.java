package tdd.groomingzone.comment.freeboardcomment.adapter.in.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tdd.groomingzone.comment.common.adapter.in.web.CommentApiDto;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.command.PostFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.PostFreeBoardCommentUseCase;
import tdd.groomingzone.member.adapter.out.persistence.MemberEntity;

@RestController
@RequestMapping("/free-board/comment")
public class PostFreeBoardCommentController {

    private final PostFreeBoardCommentUseCase postFreeBoardCommentUseCase;

    public PostFreeBoardCommentController(PostFreeBoardCommentUseCase postFreeBoardCommentUseCase) {
        this.postFreeBoardCommentUseCase = postFreeBoardCommentUseCase;
    }

    @PostMapping("/{free-board-id}")
    public void postFreeBoardComment(@AuthenticationPrincipal MemberEntity writer,
                                                                @PathVariable("free-board-id") long boardId,
                                                                @RequestBody CommentApiDto.Post dto) {
        PostFreeBoardCommentCommand postFreeBoardCommentCommand = PostFreeBoardCommentCommand.of(writer.getId(), boardId, dto.getContent());
        postFreeBoardCommentUseCase.postFreeBoardComment(postFreeBoardCommentCommand);
    }
}
