package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.post.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.post.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.DeleteFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.DeleteFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.DeleteFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

@Service
public class DeleteFreeBoardCommentService implements DeleteFreeBoardCommentUseCase {
    private final LoadMemberPort loadMemberPort;
    private final LoadFreeBoardCommentPort loadFreeBoardCommentPort;
    private final LoadFreeBoardPort loadFreeBoardPort;
    private final FreeBoardCommentPublisher freeBoardCommentPublisher;
    private final DeleteFreeBoardCommentPort deleteFreeBoardCommentPort;

    public DeleteFreeBoardCommentService(LoadMemberPort loadMemberPort, LoadFreeBoardCommentPort loadFreeBoardCommentPort, LoadFreeBoardPort loadFreeBoardPort, FreeBoardCommentPublisher freeBoardCommentPublisher, DeleteFreeBoardCommentPort deleteFreeBoardCommentPort) {
        this.loadMemberPort = loadMemberPort;
        this.loadFreeBoardCommentPort = loadFreeBoardCommentPort;
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.freeBoardCommentPublisher = freeBoardCommentPublisher;
        this.deleteFreeBoardCommentPort = deleteFreeBoardCommentPort;
    }

    @Override
    public void delete(DeleteFreeBoardCommentCommand command) {
        FreeBoardCommentEntityResult selectCommentQueryResult = loadFreeBoardCommentPort.loadFreeBoardComment(command.getCommentId());
        FreeBoardEntityQueryResult selectFreeBoardQueryResult = loadFreeBoardPort.loadFreeBoardById(selectCommentQueryResult.getBoardId());
        Member freeBoardWriter = loadMemberPort.findMemberById(selectFreeBoardQueryResult.getWriterId());
        Member commentWriter = loadMemberPort.findMemberById(selectCommentQueryResult.getWriterId());
        FreeBoardComment comment = freeBoardCommentPublisher.createFreeBoardComment(selectFreeBoardQueryResult, freeBoardWriter, commentWriter, selectCommentQueryResult.getContent(), selectCommentQueryResult.getCreatedAt(), selectCommentQueryResult.getModifiedAt());

        Member requestMember = loadMemberPort.findMemberById(command.getRequestMemberId());
        comment.checkMemberAuthority(requestMember);
        deleteFreeBoardCommentPort.delete(comment.getId());
    }
}
