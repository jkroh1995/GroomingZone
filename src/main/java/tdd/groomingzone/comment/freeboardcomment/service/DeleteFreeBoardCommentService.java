package tdd.groomingzone.comment.freeboardcomment.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.comment.freeboardcomment.dto.DeleteFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.repository.DeleteFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.repository.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class DeleteFreeBoardCommentService implements DeleteFreeBoardCommentUseCase {
    private final LoadMemberPort loadMemberPort;
    private final LoadFreeBoardCommentPort loadFreeBoardCommentPort;
    private final DeleteFreeBoardCommentPort deleteFreeBoardCommentPort;

    public DeleteFreeBoardCommentService(LoadMemberPort loadMemberPort, LoadFreeBoardCommentPort loadFreeBoardCommentPort, DeleteFreeBoardCommentPort deleteFreeBoardCommentPort) {
        this.loadMemberPort = loadMemberPort;
        this.loadFreeBoardCommentPort = loadFreeBoardCommentPort;
        this.deleteFreeBoardCommentPort = deleteFreeBoardCommentPort;
    }

    @Override
    public void delete(DeleteFreeBoardCommentCommand command) {
        FreeBoardComment freeBoardComment = loadFreeBoardCommentPort.loadFreeBoardComment(command.commentId());
        Member requestMember = loadMemberPort.findMemberByEmail(command.requestMemberEmail());
        freeBoardComment.checkMemberAuthority(requestMember);
        deleteFreeBoardCommentPort.delete(freeBoardComment.getId());
    }
}
