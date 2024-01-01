package tdd.groomingzone.comment.freeboardcomment.application.service;

import org.springframework.stereotype.Service;
import tdd.groomingzone.board.common.WriterInfo;
import tdd.groomingzone.board.freeboard.application.port.out.FreeBoardEntityQueryResult;
import tdd.groomingzone.board.freeboard.application.port.out.LoadFreeBoardPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.command.PutFreeBoardCommentCommand;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.dto.response.SingleFreeBoardCommentResponse;
import tdd.groomingzone.comment.freeboardcomment.application.port.in.usecase.PutFreeBoardCommentUseCase;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.FreeBoardCommentEntityResult;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.LoadFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.application.port.out.port.SaveFreeBoardCommentPort;
import tdd.groomingzone.comment.freeboardcomment.domain.FreeBoardComment;
import tdd.groomingzone.member.application.port.out.LoadMemberPort;
import tdd.groomingzone.member.domain.Member;

import java.time.LocalDateTime;

@Service
public class PutFreeBoardCommentService implements PutFreeBoardCommentUseCase {

    private final LoadFreeBoardCommentPort loadFreeBoardCommentPort;
    private final SaveFreeBoardCommentPort saveFreeBoardCommentPort;
    private final LoadMemberPort loadMemberPort;
    private final LoadFreeBoardPort loadFreeBoardPort;
    private final FreeBoardCommentPublisher freeBoardCommentPublisher;

    public PutFreeBoardCommentService(LoadFreeBoardCommentPort loadFreeBoardCommentPort, SaveFreeBoardCommentPort saveFreeBoardCommentPort, LoadMemberPort loadMemberPort, LoadFreeBoardPort loadFreeBoardPort, FreeBoardCommentPublisher freeBoardCommentPublisher) {
        this.loadFreeBoardCommentPort = loadFreeBoardCommentPort;
        this.saveFreeBoardCommentPort = saveFreeBoardCommentPort;
        this.loadMemberPort = loadMemberPort;
        this.loadFreeBoardPort = loadFreeBoardPort;
        this.freeBoardCommentPublisher = freeBoardCommentPublisher;
    }

    @Override
    public SingleFreeBoardCommentResponse putFreeBoard(PutFreeBoardCommentCommand command) {
        FreeBoardCommentEntityResult selectCommentQueryResult = loadFreeBoardCommentPort.loadFreeBoardComment(command.getCommentId());
        FreeBoardEntityQueryResult selectFreeBoardQueryResult = loadFreeBoardPort.loadFreeBoardById(selectCommentQueryResult.getBoardId());
        Member freeBoardWriter = loadMemberPort.findMemberById(selectFreeBoardQueryResult.getWriterId());
        Member commentWriter = loadMemberPort.findMemberById(selectCommentQueryResult.getWriterId());
        FreeBoardComment comment = freeBoardCommentPublisher.createFreeBoardComment(selectFreeBoardQueryResult, freeBoardWriter, commentWriter, command.getContent(), selectCommentQueryResult.getCreatedAt(), LocalDateTime.now());

        Member requestMember = loadMemberPort.findMemberById(command.getRequestMemberId());
        comment.checkMemberAuthority(requestMember);
        comment.modify(command.getContent(), LocalDateTime.now());

        FreeBoardCommentEntityResult saveCommentQueryResult = saveFreeBoardCommentPort.save(comment);
        return SingleFreeBoardCommentResponse.of(saveCommentQueryResult.getContent(),
                saveCommentQueryResult.getCreatedAt(),
                saveCommentQueryResult.getModifiedAt(),
                WriterInfo.of(requestMember));
    }
}
