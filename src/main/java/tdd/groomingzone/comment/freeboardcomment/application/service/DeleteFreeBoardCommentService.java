package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.DeleteFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.DeleteFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.DeleteFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
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
        FreeBoardComment freeBoardComment = loadFreeBoardCommentPort.loadFreeBoardComment(command.getCommentId());
        Member requestMember = loadMemberPort.findMemberByEmail(command.getRequestMemberEmail());
        freeBoardComment.checkMemberAuthority(requestMember);
        deleteFreeBoardCommentPort.delete(freeBoardComment.getId());
    }
}
